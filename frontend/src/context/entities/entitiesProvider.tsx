import React, { PropsWithChildren } from 'react';
import { FerienwohnungProvider } from './ferienwohnungContext';
import { KundeProvider } from './kundeContext';
import { BildProvider } from './bildContext';
import { AusstattungProvider } from './ausstattungContext';
import { LandProvider } from './landContext';

export const EntitiesProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    return (
        <FerienwohnungProvider>
            <KundeProvider>
                <BildProvider>
                    <AusstattungProvider>
                        <LandProvider>
                            {children}
                        </LandProvider>
                    </AusstattungProvider>
                </BildProvider>
            </KundeProvider>
        </FerienwohnungProvider>
    );
};
