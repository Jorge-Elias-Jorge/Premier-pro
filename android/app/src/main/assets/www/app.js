const videoInput = document.getElementById('videoInput');
const importBtn = document.getElementById('importBtn');
const video = document.getElementById('video');
const empty = document.getElementById('empty');
const generateBtn = document.getElementById('generateBtn');
const srtBtn = document.getElementById('srtBtn');
const statusEl = document.getElementById('status');
const cueList = document.getElementById('cueList');
const apiKeyInput = document.getElementById('apiKey');
const language = document.getElementById('language');
const playhead = document.getElementById('playhead');

let mediaFile = null;
let cues = [];

apiKeyInput.value = localStorage.getItem('groqApiKey') || '';
apiKeyInput.addEventListener('input', () => localStorage.setItem('groqApiKey', apiKeyInput.value));

importBtn.addEventListener('click', () => videoInput.click());

videoInput.addEventListener('change', () => {
  mediaFile = videoInput.files[0];
  if (!mediaFile) return;
  video.src = URL.createObjectURL(mediaFile);
  empty.hidden = true;
  generateBtn.disabled = false;
});

video.addEventListener('timeupdate', () => {
  if (!video.duration) return;
  playhead.style.left = `${(video.currentTime / video.duration) * 100}%`;
});

function setTime(seconds) {
  const d = new Date(seconds * 1000);
  const p = (n, l = 2) => String(n).padStart(l, '0');
  return `${p(d.getUTCHours())}:${p(d.getUTCMinutes())}:${p(d.getUTCSeconds())},${p(d.getUTCMilliseconds(), 3)}`;
}

function renderCues() {
  cueList.innerHTML = '';
  cues.forEach((cue, index) => {
    const li = document.createElement('li');
    const input = document.createElement('input');
    input.value = cue.text;
    input.addEventListener('input', () => {
      cues[index].text = input.value;
    });
    li.append(input);
    cueList.append(li);
  });
  srtBtn.disabled = cues.length === 0;
}

generateBtn.addEventListener('click', async () => {
  if (!mediaFile) return;
  const apiKey = apiKeyInput.value.trim();
  if (!apiKey) {
    statusEl.textContent = 'Insira sua chave da API Groq.';
    return;
  }

  statusEl.textContent = 'Analisando o áudio…';
  generateBtn.disabled = true;

  try {
    const form = new FormData();
    form.append('file', mediaFile, mediaFile.name);
    form.append('model', 'whisper-large-v3');
    form.append('language', language.value);
    form.append('response_format', 'verbose_json');
    form.append('timestamp_granularities[]', 'segment');

    const response = await fetch('https://api.groq.com/openai/v1/audio/transcriptions', {
      method: 'POST',
      headers: { Authorization: `Bearer ${apiKey}` },
      body: form
    });

    if (!response.ok) throw new Error(`Erro ${response.status}`);
    const data = await response.json();
    cues = (data.segments || []).map(s => ({ start: s.start, end: s.end, text: s.text.trim() }));
    renderCues();
    statusEl.textContent = `${cues.length} legendas geradas.`;
  } catch (error) {
    statusEl.textContent = `Falha: ${error.message}`;
  } finally {
    generateBtn.disabled = false;
  }
});

srtBtn.addEventListener('click', () => {
  const srt = cues.map((cue, i) =>
    `${i + 1}\n${setTime(cue.start)} --> ${setTime(cue.end)}\n${cue.text}\n`
  ).join('\n');

  const blob = new Blob([srt], { type: 'text/plain;charset=utf-8' });
  const a = document.createElement('a');
  a.href = URL.createObjectURL(blob);
  a.download = 'legendas.srt';
  a.click();
});
