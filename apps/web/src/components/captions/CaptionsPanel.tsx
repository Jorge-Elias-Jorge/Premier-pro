"use client";
import { useState } from "react";
import { CaptionCue, generateCaptions, toSrt } from "../../services/captions/whisperCaptions";

export function CaptionsPanel({ videoUrl }: { videoUrl?: string }) {
  const [cues, setCues] = useState<CaptionCue[]>([]);
  const [language, setLanguage] = useState("pt");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function handleGenerate(e: React.ChangeEvent<HTMLInputElement>) {
    const file = e.target.files?.[0];
    if (!file) return;
    setLoading(true); setError(null);
    try { setCues(await generateCaptions(file, language)); }
    catch (err: any) { setError(err.message || "Não foi possível gerar legendas."); }
    finally { setLoading(false); }
  }

  function downloadSrt() {
    const blob = new Blob([toSrt(cues)], { type: "text/plain;charset=utf-8" });
    const a = document.createElement("a");
    a.href = URL.createObjectURL(blob);
    a.download = "legendas.srt";
    a.click();
  }

  return (
    <div>
      <h3>Legendas com IA</h3>
      <select value={language} onChange={(e) => setLanguage(e.target.value)}>
        <option value="pt">Português</option>
        <option value="en">Inglês</option>
        <option value="es">Espanhol</option>
      </select>
      <input type="file" accept="video/*,audio/*" onChange={handleGenerate} disabled={loading} />
      {loading && <p>Analisando áudio…</p>}
      {error && <p role="alert">{error}</p>}
      {cues.length > 0 && <button className="opencut-button" onClick={downloadSrt}>Baixar SRT</button>}
      <ul>
        {cues.map((cue, i) => (
          <li key={i} className="opencut-caption-chip">
            <input value={cue.text} onChange={(e) => { const n = [...cues]; n[i] = { ...cue, text: e.target.value }; setCues(n); }} />
          </li>
        ))}
      </ul>
    </div>
  );
}
