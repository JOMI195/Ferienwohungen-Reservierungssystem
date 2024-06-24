export interface Ferienwohnung {
    ferienwohnungs_id: number;
    ferienwohnungsname: string;
    mietpreis: number;
    zimmer: number;
    größe: number;
    straße: string;
    hausnummer: string;
    postleitzahl: string;
    ort: string;
    landname: string;
    avgSterne: number | null;
}

export interface FerienwohnungFiltered {
    ferienwohnungs_id: number;
}

export interface Bild {
    linkURL: string;
    ferienwohnungs_id: number;
}

export interface Ausstattung {
    ausstattungsname: string;
}

export interface Land {
    landname: string;
}

export interface Kunde {
    email: string;
    passwort: string;
    vorname: string;
    nachname: string;
    iban: string;
    newsletter: string;
    straße: string;
    hausnummer: string;
    postleitzahl: string;
    ort: string;
    landname: string;
}

export interface Buchung {
    buchnungsnummer: number;
    ferienwohnungs_id: number;
    email: string;
    buchungsdatum: string;
    startdatum: string;
    enddatum: string;
    sterne: number;
    bewertungsdatum: string;
    rechnungsnummer: number;
    rechnungsbetrag: number;
    rechnungsdatum: string;
}

export interface BuchungCreate {
    ferienwohnungs_id: number;
    email: string;
    startdatum: string;
    enddatum: string;
    rechnungsbetrag: number;
}

export interface Besitzt {
    ferienwohnungs_id: number;
    ausstattungsname: string;
}

export interface LiegtInDerNaeheVon {
    ferienwohnungs_id: number;
    touristenattraktionsname: string;
    entfernung: number;
}

export interface Touristenattraktion {
    touristenattraktionsname: string;
    beschreibung?: string;
}