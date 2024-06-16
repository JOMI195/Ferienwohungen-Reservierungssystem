declare module "@mui/material/styles" {
  interface Theme {
    layout: {
      appbar: {
        height: number
      },
      footer: {
        height: number
      }
    };
  }

  interface ThemeOptions {
    layout: {
      appbar: {
        height: number
      },
      footer: {
        height: number
      }
    };
  }
}

export const commonThemeOptions = {
  typography: {
    fontFamily: [
      'Open Sans',
    ].join(','),
  },
  shape: {
    borderRadius: 3,
  },
  layout: {
    appbar: {
      height: 80,
    },
    footer: {
      height: 50
    }
  },
}