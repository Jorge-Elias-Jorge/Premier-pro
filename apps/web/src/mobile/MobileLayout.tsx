"use client";
import { useState } from "react";
import { CaptionsPanel } from "../components/captions/CaptionsPanel";

export function MobileLayout({ videoUrl, onMediaSelect, children }: {
  videoUrl?: string;
  onMediaSelect?: (file: File) => void;
  children?: React.ReactNode;
}) {
  const [panel, setPanel] = useState<"none" | "media" | "captions">("none");
  return (
    <div className="opencut-workspace">
      <header className="opencut-toolbar">
        <button className="opencut-button" onClick={() => setPanel(panel === "media" ? "none" : "media")}>Mídia</button>
        <button className="opencut-button" onClick={() => setPanel(panel === "captions" ? "none" : "captions")}>Legendas IA</button>
        <button className="opencut-button">Exportar</button>
      </header>
      <section className="opencut-preview">
        {videoUrl ? <video src={videoUrl} controls playsInline /> : <p>Importe um vídeo para começar</p>}
        {children}
      </section>
      <section className="opencut-timeline"><div className="opencut-track"><span>Linha do tempo</span></div></section>
      <div className={`opencut-sidebar ${panel === "media" ? "open" : ""}`}>
        <h3>Importar mídia</h3>
        <input type="file" accept="video/*,audio/*,image/*" onChange={(e) => { const f = e.target.files?.[0]; if (f) onMediaSelect?.(f); }} />
      </div>
      <div className={`opencut-properties ${panel === "captions" ? "open" : ""}`}>
        <CaptionsPanel videoUrl={videoUrl} />
      </div>
    </div>
  );
}
