import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

const envDir = path.resolve(__dirname, '../');

export default defineConfig({
  envDir,
  plugins: [react()],
  build: {},
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
})
