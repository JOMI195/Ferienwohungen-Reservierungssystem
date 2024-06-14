import { useState, useEffect } from 'react';
import light from '../themes/lightTheme';
import dark from '../themes/darkTheme';

type ColorMode = 'light' | 'dark';
type Theme = typeof light | typeof dark;

const useColorTheme = (): Theme => {
    const prefersDarkMode = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    const [colorMode, setColorMode] = useState<ColorMode>(prefersDarkMode ? 'light' : 'dark'); // TODO: change back to correct

    /*     useEffect(() => {
            const handleThemeChange = (e: MediaQueryListEvent) => {
                setColorMode(e.matches ? 'dark' : 'light');
            };
    
            window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', handleThemeChange);
    
            return () => {
                window.matchMedia('(prefers-color-scheme: dark)').removeEventListener('change', handleThemeChange);
            };
        }, []); */

    return colorMode === 'dark' ? dark : light;
};

export default useColorTheme;
