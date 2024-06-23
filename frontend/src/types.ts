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