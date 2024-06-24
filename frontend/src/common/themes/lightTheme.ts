import { createTheme, responsiveFontSizes } from '@mui/material/styles';
import { commonThemeOptions } from './commonThemeOptions';

const lightTheme = createTheme({
    ...commonThemeOptions,
    palette: {
        mode: 'light',
        primary: {
            main: '#666cff',
        },
        secondary: {
            main: '#6d789d',
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
