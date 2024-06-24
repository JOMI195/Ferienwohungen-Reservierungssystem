import React, { PropsWithChildren, createContext, useContext } from 'react';
import { useState } from 'react';
import LightModeIcon from '@mui/icons-material/LightMode';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import light from '@/common/themes/lightTheme';
import dark from '@/common/themes/darkTheme';
import { ThemeProvider } from '@mui/material';

type ColorMode = 'light' | 'dark';
type Theme = typeof light | typeof dark;
type IconComponent = typeof LightModeIcon | typeof DarkModeIcon

interface ColorThemeContextType {
    theme: Theme;
    toggleColorMode: () => void;
    colorMode: ColorMode;
    iconComponent: IconComponent;
}

const ColorThemeContext = createContext<ColorThemeContextType | undefined>(undefined);

export const useColorThemeContext = () => {
    const context = useContext(ColorThemeContext);
    if (!context) {
        throw new Error('useColorThemeContext must be used within a ColorThemeProvider');
    }
    return context;
};

export const ColorThemeProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    //const prefersDarkMode = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    const prefersDarkMode = false;
    const [colorMode, setColorMode] = useState<ColorMode>(prefersDarkMode ? 'dark' : 'light');
    const [iconComponent, setIconComponent] = useState<IconComponent>(prefersDarkMode ? DarkModeIcon : LightModeIcon);

    const toggleColorMode = () => {
        const newMode: ColorMode = colorMode === 'light' ? 'dark' : 'light';
        setColorMode(newMode);
        setIconComponent(newMode === 'light' ? LightModeIcon : DarkModeIcon);
    };

    const contextValue: ColorThemeContextType = {
        theme: colorMode === 'dark' ? dark : light,
        colorMode,
        toggleColorMode,
        iconComponent,
    };

    return (
        <ColorThemeContext.Provider value={contextValue}>
            <ThemeProvider theme={contextValue.theme}>
                {children}
            </ThemeProvider>
        </ColorThemeContext.Provider>
    );
};
