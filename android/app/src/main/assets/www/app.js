const $=id=>document.getElementById(id);
const mediaInput=$('mediaInput'),video=$('video'),empty=$('empty'),track=$('track'),head=$('head'),overlay=$('textOverlay');
let clips=[],caps=[],sel=null,capId=0;

const uid=()=>Math.random().toString(36).slice(2,10);
const fmt=s=>{const d=new Date(Math.max(0,s)*1000),p=(n,l=2)=>String(n).padStart(l,'0');return `${p(d.getUTCHours())}:${p(d.getUTCMinutes())}:${p(d.getUTCSeconds())},${p(d.getUTCMilliseconds(),3)}`};
const getSel=()=>clips.find(c=>c.id===sel)||null;

function save(){try{localStorage.setItem('premierpro',JSON.stringify({clips:clips.map(({url,...r})=>r),caps}))}catch(e){}}

function render(){
track.innerHTML='';
clips.forEach((c,i)=>{const e=document.createElement('div');e.className='clip'+(c.id===sel?' sel':'');e.innerHTML=`<strong>${c.name}</strong><span>${c.start.toFixed(1)}s - ${c.end.toFixed(1)}s</span>`;e.onclick=()=>select(c.id);e.dataset.index=i;addDrag(e);track.append(e)});
const s=getSel();
['splitBtn','dupBtn','delBtn'].forEach(id=>$(id).disabled=!s);
$('splitBtn').disabled=!s||s.type!=='video';
if(s){$('noClip').hidden=true;$('clipForm').hidden=false;$('cStart').value=s.start.toFixed(1);$('cEnd').value=s.end.toFixed(1);$('cText').value=s.text||'';overlay.textContent=s.text||''}else{$('noClip').hidden=false;$('clipForm').hidden=true}
save();
}

function select(id){sel=id;const c=getSel();if(c&&c.url){video.src=c.url;video.currentTime=c.start;empty.hidden=true;overlay.textContent=c.text||''}render()}

function addDrag(element){
let startX=0,startIndex=0,dragging=false;
element.addEventListener('pointerdown',e=>{dragging=true;startX=e.clientX;startIndex=Number(element.dataset.index);element.setPointerCapture(e.pointerId)});
element.addEventListener('pointermove',e=>{if(!dragging)return;const delta=e.clientX-startX;if(Math.abs(delta)>80){const target=startIndex+(delta>0?1:-1);if(target>=0&&target<clips.length){const item=clips.splice(startIndex,1)[0];clips.splice(target,0,item);sel=item.id;startIndex=target;startX=e.clientX;render()}}});
element.addEventListener('pointerup',()=>dragging=false);
}

$('importBtn').onclick=()=>mediaInput.click();
mediaInput.onchange=()=>{[...mediaInput.files].forEach(f=>{const url=URL.createObjectURL(f);const type=f.type.startsWith('video')?'video':f.type.startsWith('audio')?'audio':'image';clips.push({id:uid(),name:f.name,type,url,start:0,end:type==='image'?5:0,text:''});if(type!=='image'){const m=document.createElement(type==='video'?'video':'audio');m.src=url;m.onloadedmetadata=()=>{const c=clips.find(x=>x.url===url);if(c){c.end=m.duration||10;render()}}}});mediaInput.value='';if(!sel&&clips.length)select(clips[0].id);render()};

video.ontimeupdate=()=>{const c=getSel();if(c&&video.duration)head.style.left=(video.currentTime/video.duration*100)+'%'};

$('splitBtn').onclick=()=>{const c=getSel();if(!c||c.type!=='video')return;const p=video.currentTime;if(p<=c.start||p>=c.end)return;const right={...c,id:uid(),start:p};c.end=p;clips.splice(clips.indexOf(c)+1,0,right);render()};
$('dupBtn').onclick=()=>{const c=getSel();if(!c)return;clips.splice(clips.indexOf(c)+1,0,{...c,id:uid()});render()};
$('delBtn').onclick=()=>{const c=getSel();if(!c)return;clips=clips.filter(x=>x.id!==c.id);sel=null;video.removeAttribute('src');empty.hidden=false;overlay.textContent='';render()};
$('textBtn').onclick=()=>{const c=getSel();if(!c)return;c.text=c.text||'Novo texto';overlay.textContent=c.text;render()};
$('cStart').oninput=e=>{const c=getSel();if(c)c.start=Number(e.target.value)||0;render()};
$('cEnd').oninput=e=>{const c=getSel();if(c)c.end=Number(e.target.value)||0;render()};
$('cText').oninput=e=>{const c=getSel();if(c){c.text=e.target.value;overlay.textContent=c.text}};

$('capBtn').onclick=()=>{const c=getSel();const start=c?c.start:video.currentTime||0;const end=c?c.end:start+3;const cap={id:++capId,start,end,text:'Nova legenda'};caps.push(cap);const d=document.createElement('div');d.className='cap';d.innerHTML=`<div class="row"><input type="number" step="0.1" value="${start}"/><input type="number" step="0.1" value="${end}"/></div><textarea rows="2">${cap.text}</textarea>`;const ins=d.querySelectorAll('input'),ta=d.querySelector('textarea');ins[0].oninput=()=>cap.start=Number(ins[0].value);ins[1].oninput=()=>cap.end=Number(ins[1].value);ta.oninput=()=>cap.text=ta.value;$('caps').append(d);save()};

$('srtBtn').onclick=()=>{const srt=caps.map((c,i)=>`${i+1}\n${fmt(c.start)} --> ${fmt(c.end)}\n${c.text}\n`).join('\n');const b=new Blob([srt],{type:'text/plain;charset=utf-8'});const a=document.createElement('a');a.href=URL.createObjectURL(b);a.download='legendas.srt';a.click()};
$('saveBtn').onclick=()=>{save();alert('Projeto salvo neste dispositivo.')};

function playSequence(onDone){
const list=clips.filter(c=>c.url&&c.type!=='image');
if(!list.length){onDone&&onDone();return}
let i=0;
const next=()=>{if(i>=list.length){video.pause();onDone&&onDone();return}
const c=list[i++];video.src=c.url;video.currentTime=c.start;
video.onended=()=>next();
video.ontimeupdate=()=>{if(video.currentTime>=c.end){next()}};
video.play()};
next();
}

$('playBtn').onclick=()=>playSequence();

$('exportBtn').onclick=()=>{
const list=clips.filter(c=>c.url&&c.type!=='image');
if(!list.length)return alert('Importe pelo menos um vídeo ou áudio.');
const canvas=$('renderCanvas');
canvas.width=720;canvas.height=1280;
const ctx=canvas.getContext('2d');
const stream=canvas.captureStream(30);
try{const vs=video.captureStream();if(vs.getAudioTracks().length)stream.addTrack(vs.getAudioTracks()[0])}catch(e){}
const mime=MediaRecorder.isTypeSupported('video/webm;codecs=vp9')?'video/webm;codecs=vp9':'video/webm';
const recorder=new MediaRecorder(stream,{mimeType:mime});
const chunks=[];
recorder.ondataavailable=e=>chunks.push(e.data);
recorder.onstop=()=>{const b=new Blob(chunks,{type:'video/webm'});const a=document.createElement('a');a.href=URL.createObjectURL(b);a.download='premier-pro.webm';a.click()};
recorder.start();
let i=0;
const draw=()=>{if(video.readyState>=2)ctx.drawImage(video,0,0,canvas.width,canvas.height);const c=clips.find(x=>x.url===video.src);if(c&&c.text){ctx.font='bold 42px system-ui';ctx.fillStyle='#fff';ctx.strokeStyle='#000';ctx.lineWidth=6;ctx.textAlign='center';ctx.strokeText(c.text,canvas.width/2,canvas.height*0.82);ctx.fillText(c.text,canvas.width/2,canvas.height*0.82)}};
const loop=setInterval(draw,33);
const next=()=>{if(i>=list.length){clearInterval(loop);recorder.stop();return}
const c=list[i++];video.src=c.url;video.currentTime=c.start;
video.onended=()=>next();
video.ontimeupdate=()=>{if(video.currentTime>=c.end)next()};
video.play()};
next();
};

try{const saved=JSON.parse(localStorage.getItem('premierpro')||'null');if(saved){caps=saved.caps||[];caps.forEach(c=>{const d=document.createElement('div');d.className='cap';d.innerHTML=`<div class="row"><input type="number" step="0.1" value="${c.start}"/><input type="number" step="0.1" value="${c.end}"/></div><textarea rows="2">${c.text}</textarea>`;const ins=d.querySelectorAll('input'),ta=d.querySelector('textarea');ins[0].oninput=()=>c.start=Number(ins[0].value);ins[1].oninput=()=>c.end=Number(ins[1].value);ta.oninput=()=>c.text=ta.value;$('caps').append(d)})}}catch(e){}
render();
