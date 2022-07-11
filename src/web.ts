import { WebPlugin } from '@capacitor/core';

import type { SnapacitorLoginKitPlugin } from './definitions';

export class SnapacitorLoginKitWeb extends WebPlugin
  implements SnapacitorLoginKitPlugin {
  async login(): Promise<{ value: string }> {
    return new Promise(resolve => {
      resolve({ value: 'no web implementation' });
    });
  }

  async getUserInfo(): Promise<{ value: string }> {
    return new Promise(resolve => {
      resolve({ value: 'no web implementation' });
    });
  }
}
