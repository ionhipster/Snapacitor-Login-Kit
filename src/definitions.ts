export interface SnapacitorLoginKitPlugin {
  login(): Promise<{ value: string }>;
  getUserInfo(): Promise<{ value: string }>;
}
