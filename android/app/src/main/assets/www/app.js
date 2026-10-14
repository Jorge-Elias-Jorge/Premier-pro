const mediaInput = document.getElementById('mediaInput');
const importBtn = document.getElementById('importBtn');
const video = document.getElementById('video');
const emptyState = document.getElementById('emptyState');
const clipsEl = document.getElementById('clips');
const playhead = document.getElementById('playhead');
const splitBtn = document.getElementById('splitBtn');
const deleteBtn = document.getElementById('deleteBtn');
const textBtn = document.getElementById('textBtn');
const captionBtn = document.getElementById('captionBtn');
const srtBtn = document.getElementById('srtBtn');
const clipStart = document.getElementById('clipStart');
const clipEnd = document.getElementById('clipEnd');
const clipText = document.getElementById('clipText');
const propertiesEmpty = document.getElementById('propertiesEmpty');
const propertiesForm = document.getElementById('propertiesForm');
const captionList = document.getElementById('captionList');

let clips = [];
let selectedClipId = null;
let captionId = 0;

function uid() {
  return Math.random().toString(36).slice(2, 10);
}

function formatTime(seconds) {
  const d = new Date(Math.max(0, seconds) * 1000);
  const p = (n, l = 2) => String(n).padStart(l, '0');
  return `${p(d.getUTCHours())}:${p(d.getUTCMinutes())}:${p(d.getUTCSeconds())},${p(d.getUTCMilliseconds(), 3)}`;
}

function selectedClip() {
  return clips.find(clip => clip.id === selectedClipId) || null;
}

function render() {
  clipsEl.innerHTML = '';

  clips.forEach(clip => {
    const element = document.createElement('div');
    element.className = 'clip' + (clip.id === selectedClipId ? ' selected' : '');
    element.innerHTML = `<strong>${clip.name}</strong><span>${clip.start.toFixed(1)}s - ${clip.end.toFixed(1)}s</span>`;
    element.addEventListener('click', () => selectClip(clip.id));
    clipsEl.append(element);
  });

  const selected = selectedClip();
  splitBtn.disabled = !selected || selected.type !== 'video';
  deleteBtn.disabled = !selected;
  srtBtn.disabled = clips.length === 0;

  if (selected) {
    propertiesEmpty.hidden = true;
    propertiesForm.hidden = false;
    clipStart.value = selected.start.toFixed(1);
    clipEnd.value = selected.end.toFixed(1);
    clipText.value = selected.text || '';
  } else {
    propertiesEmpty.hidden = false;
    propertiesForm.hidden = true;
  }
}

function selectClip(id) {
  selectedClipId = id;
  const clip = selectedClip();
  if (clip && clip.url) {
    video.src = clip.url;
    video.currentTime = clip.start;
    emptyState.hidden = true;
  }
  render();
}

importBtn.addEventListener('click', () => mediaInput.click());

mediaInput.addEventListener('change', () => {
  Array.from(mediaInput.files).forEach(file => {
    const url = URL.createObjectURL(file);
    const type = file.type.startsWith('video') ? 'video' : file.type.startsWith('audio') ? 'audio' : 'image';

    clips.push({
      id: uid(),
      name: file.name,
      type,
      url,
      start: 0,
      end: type === 'image' ? 5 : 0,
      text: ''
    });

    if (type !== 'image') {
      const media = document.createElement(type === 'video' ? 'video' : 'audio');
      media.src = url;
      media.addEventListener('loadedmetadata', () => {
        const clip = clips.find(item => item.url === url);
        if (clip) {
          clip.end = media.duration || 10;
          render();
        }
      });
    }
  });

  mediaInput.value = '';
  if (!selectedClipId && clips.length) selectClip(clips[0].id);
  render();
});

video.addEventListener('timeupdate', () => {
  const selected = selectedClip();
  if (!selected || !video.duration) return;
  playhead.style.left = `${(video.currentTime / video.duration) * 100}%`;
});

splitBtn.addEventListener('click', () => {
  const clip = selectedClip();
  if (!clip || clip.type !== 'video') return;

  const point = video.currentTime;
  if (point <= clip.start || point >= clip.end) return;

  const right = { ...clip, id: uid(), start: point };
  clip.end = point;
  clips.splice(clips.indexOf(clip) + 1, 0, right);
  render();
});

deleteBtn.addEventListener('click', () => {
  const clip = selectedClip();
  if (!clip) return;
  clips = clips.filter(item => item.id !== clip.id);
  selectedClipId = null;
  video.removeAttribute('src');
  emptyState.hidden = false;
  render();
});

textBtn.addEventListener('click', () => {
  const clip = selectedClip();
  if (!clip) return;
  clip.text = clip.text || 'Novo texto';
  render();
});

captionBtn.addEventListener('click', () => {
  const clip = selectedClip();
  const start = clip ? clip.start : video.currentTime || 0;
  const end = clip ? clip.end : start + 3;

  const item = { id: ++captionId, start, end, text: 'Nova legenda' };

  const element = document.createElement('div');
  element.className = 'caption';
  element.innerHTML = `
    <div class="times">
      <input type="number" step="0.1" value="${item.start}" aria-label="Início" />
      <input type="number" step="0.1" value="${item.end}" aria-label="Fim" />
    </div>
    <textarea rows="2">${item.text}</textarea>
  `;

  const inputs = element.querySelectorAll('input');
  const textarea = element.querySelector('textarea');

  inputs[0].addEventListener('input', () => item.start = Number(inputs[0].value));
  inputs[1].addEventListener('input', () => item.end = Number(inputs[1].value));
  textarea.addEventListener('input', () => item.text = textarea.value);

  captionList.append(element);
  srtBtn.disabled = false;
});

clipStart.addEventListener('input', () => {
  const clip = selectedClip();
  if (clip) clip.start = Number(clipStart.value) || 0;
  render();
});

clipEnd.addEventListener('input', () => {
  const clip = selectedClip();
  if (clip) clip.end = Number(clipEnd.value) || 0;
  render();
});

clipText.addEventListener('input', () => {
  const clip = selectedClip();
  if (clip) clip.text = clipText.value;
});

srtBtn.addEventListener('click', () => {
  const captionItems = Array.from(document.querySelectorAll('.caption'));
  const cues = captionItems.map(element => {
    const inputs = element.querySelectorAll('input');
    const textarea = element.querySelector('textarea');
    return {
      start: Number(inputs[0].value),
      end: Number(inputs[1].value),
      text: textarea.value
    };
  });

  const srt = cues.map((cue, index) =>
    `${index + 1}\n${formatTime(cue.start)} --> ${formatTime(cue.end)}\n${cue.text}\n`
  ).join('\n');

  const blob = new Blob([srt], { type: 'text/plain;charset=utf-8' });
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = 'legendas.srt';
  link.click();
});

render();
