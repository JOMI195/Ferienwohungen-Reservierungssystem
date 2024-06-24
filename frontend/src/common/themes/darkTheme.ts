import { createTheme, responsiveFontSizes } from '@mui/material/styles';
import { commonThemeOptions } from './commonThemeOptions';

const darkTheme = createTheme({
    ...commonThemeOptions,
    palette: {
        mode: 'dark',
        primary: {
            main: '#eaeaff',
        },
        secondary: {
            main: '#e7e3fc',
        },
        text: {
            primary: '#eaeaff',
            secondary: '#e7e3fc',
            disabled: '#e7e3fc',
        },
        background: {
            default: '#252627',
            paper: '#131b23',
        },
    },
});

const dark = responsiveFontSizes(darkTheme);

export default dark;
;