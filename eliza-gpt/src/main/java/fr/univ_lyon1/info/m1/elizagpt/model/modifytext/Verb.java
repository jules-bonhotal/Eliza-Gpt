package fr.univ_lyon1.info.m1.elizagpt.model.modifytext;

/**
 * Représente un verbe avec sa conjugaison de la première personne du singulier (Je)
 * vers la deuxième personne du pluriel (Vous).
 */
public class Verb {
    /** Conjugaison à la première personne du singulier. */
    private final String firstSingular;
    
    /** Conjugaison à la deuxième personne du pluriel. */
    private final String secondPlural;

    /**
     * Constructeur de la classe Verb.
     *
     * @param firstSingular Conjugaison à la première personne du singulier.
     * @param secondPlural Conjugaison à la deuxième personne du pluriel.
     */
    public Verb(final String firstSingular, final String secondPlural) {
        this.firstSingular = firstSingular;
        this.secondPlural = secondPlural;
    }

    /**
     * Obtient la conjugaison à la première personne du singulier.
     *
     * @return Conjugaison à la première personne du singulier.
     */
    public String getFirstSingular() {
        return firstSingular;
    }

    /**
     * Obtient la conjugaison à la deuxième personne du pluriel.
     *
     * @return Conjugaison à la deuxième personne du pluriel.
     */
    public String getSecondPlural() {
        return secondPlural;
    }
}
