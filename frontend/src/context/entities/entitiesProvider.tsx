import React, { PropsWithChildren } from 'react';
import { FerienwohnungProvider } from './ferienwohnungContext';
import { KundeProvider } from './kundeContext';
import { BildProvider } from './bildContext';
import { AusstattungProvider } from './ausstattungContext';
import { LandProvider } from './landContext';
import { BuchungProvider } from './buchungContext';
import { BesitztProvider } from './besitztContext';
import { TouristenattraktionProvider } from './touristenattraktionContext';
import { LiegtInDerNaeheVonProvider } from './liegtInDerNaeheVonContext';

export const EntitiesProvider: React.FC<PropsWithChildren<{}>> = ({ children }) => {
    return (
        <FerienwohnungProvider>
            <KundeProvider>
                <BuchungProvider>
                    <BildProvider>
                        <AusstattungProvider>
                            <LandProvider>
                                <BesitztProvider>
                                    <TouristenattraktionProvider>
                                        <LiegtInDerNaeheVonProvider>
                                            {children}
                                        </LiegtInDerNaeheVonProvider>
                                    </TouristenattraktionProvider>
                                </BesitztProvider>
                            </LandProvider>
                        </AusstattungProvider>
                    </BildProvider>
                </BuchungProvider>
            </KundeProvider>
        </FerienwohnungProvider>
    );
};
