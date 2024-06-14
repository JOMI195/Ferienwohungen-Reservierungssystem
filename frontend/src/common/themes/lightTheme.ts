import { createTheme, responsiveFontSizes } from '@mui/material/styles';
import { commonThemeOptions } from './commonThemeOptions';

const lightTheme = createTheme({
    ...commonThemeOptions,
    palette: {
        mode: 'light',
        primary: {
            main: '#666cff',
            dark: '#5a5fee',
            light: '#787eff',
            contrastText: '#ffffff',
        },
        secondary: {
            main: '#6d789d',
            dark: '#606a7c',
            light: '#7f889b',
            contrastText: '#ffffff',
        },
        text: {
            primary: '#4c4e64',
            secondary: '#4c4e64',
            disabled: '#4c4e64',
        },
        background: {
            paper: '#ffffff',
            default: '#f7f7f9',
        },
    },
});

const light = responsiveFontSizes(lightTheme);

export default light;
;
