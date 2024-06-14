import { createTheme, responsiveFontSizes } from '@mui/material/styles';
import { commonThemeOptions } from './commonThemeOptions';

const darkTheme = createTheme({
    ...commonThemeOptions,
    palette: {
        mode: 'dark',
        primary: {
            main: '#eaeaff',
            dark: '#eaeaff',
            light: '#eaeaff',
            contrastText: '#ffffff',
        },
        secondary: {
            main: '#e7e3fc',
            dark: '#eaeaff',
            light: '#eaeaff',
            contrastText: '#ffffff',
        },
        text: {
            primary: '#eaeaff',
            secondary: '#e7e3fc',
            disabled: '#e7e3fc',
        },
        background: {
            paper: '#30334e',
            default: '#282a42',
        },
    },
});

const dark = responsiveFontSizes(darkTheme);

export default dark;
;