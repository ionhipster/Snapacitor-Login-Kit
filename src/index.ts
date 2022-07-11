import { registerPlugin } from '@capacitor/core';

import type { SnapacitorLoginKitPlugin } from './definitions';

const SnapacitorLoginKit = registerPlugin<SnapacitorLoginKitPlugin>(
  'SnapacitorLoginKit',
  {
    web: () => import('./web').then(m => new m.SnapacitorLoginKitWeb()),
  },
);

export * from './definitions';
export { SnapacitorLoginKit };
