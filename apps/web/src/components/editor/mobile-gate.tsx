"use client";

interface MobileGateProps {
  children: React.ReactNode;
}

/**
 * Permite aceder ao editor em dispositivos móveis.
 * A adaptação completa da interface será feita progressivamente.
 */
export function MobileGate({ children }: MobileGateProps) {
  return <>{children}</>;
}