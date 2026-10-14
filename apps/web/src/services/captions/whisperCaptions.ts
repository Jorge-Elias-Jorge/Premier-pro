export type CaptionCue = { start: number; end: number; text: string };

export async function generateCaptions(mediaFile: File, language = "pt"): Promise<CaptionCue[]> {
  const env = (import.meta as any).env || {};
  const apiKey = env.VITE_WHISPER_API_KEY || env.VITE_GROQ_API_KEY;
  if (!apiKey) throw new Error("Configure VITE_GROQ_API_KEY no arquivo .env.local");

  const form = new FormData();
  form.append("file", mediaFile, mediaFile.name);
  form.append("model", "whisper-large-v3");
  form.append("language", language);
  form.append("response_format", "verbose_json");
  form.append("timestamp_granularities[]", "segment");

  const response = await fetch("https://api.groq.com/openai/v1/audio/transcriptions", {
    method: "POST",
    headers: { Authorization: `Bearer ${apiKey}` },
    body: form,
  });

  if (!response.ok) throw new Error(`Falha ao gerar legendas: ${response.status}`);
  const data = await response.json();
  return (data.segments || []).map((s: any) => ({ start: s.start, end: s.end, text: s.text.trim() }));
}

export function toSrt(cues: CaptionCue[]): string {
  const time = (seconds: number) => {
    const d = new Date(seconds * 1000);
    const p = (n: number, l = 2) => String(n).padStart(l, "0");
    return `${p(d.getUTCHours())}:${p(d.getUTCMinutes())}:${p(d.getUTCSeconds())},${p(d.getUTCMilliseconds(), 3)}`;
  };
  return cues.map((c, i) => `${i + 1}\n${time(c.start)} --> ${time(c.end)}\n${c.text}\n`).join("\n");
}
