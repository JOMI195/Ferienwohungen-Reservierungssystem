import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

const envDir = path.resolve(__dirname, '../');

export default defineConfig({
  envDir,
  plugins: [react()],
  server: {
    host: true,
    port: 3000,
    watch: {
      usePolling: true
    }
  },
  build: {},
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  optimizeDeps: {
    include: ['@mui/material/Tooltip', '@emotion/styled', '@mui/material/Unstable_Grid2'],
  },
})
