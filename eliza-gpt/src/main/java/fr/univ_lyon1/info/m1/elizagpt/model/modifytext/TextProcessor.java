package fr.univ_lyon1.info.m1.elizagpt.model.modifytext;

/**
 * Classe utilitaire pour le traitement du texte.
 */
public final class TextProcessor {

    private TextProcessor() {
        // Constructeur privé pour une classe utilitaire
    }

    /**
     * Modifie les pronoms dans le texte.
     *
     * @param text Le texte d'entrée.
     * @return Le texte modifié.
     */
    public static String modifyPronouns(final String text) {
        String processedText = text
                .replaceAll("[Jj]e ", "vous ")
                .replaceAll("([Jj]')", "vous ");
        return processedText;
    }

    /**
     * Modifie les verbes dans le texte.
     *
     * @param text Le texte d'entrée.
     * @return Le texte modifié.
     */
    public static String modifyVerbs(final String text) {
        String processedText = text;
        for (Verb v : VerbCollection.getVerbs()) {
            processedText = processedText
                .replaceAll("\\b" + v.getFirstSingular() + "\\b", v.getSecondPlural());
        }
        return processedText;
    }

    /**
     * Modifie les déterminants dans le texte.
     *
     * @param text Le texte d'entrée.
     * @return Le texte modifié.
     */
    public static String modifyDeterminers(final String text) {
        String processedText = text
                .replaceAll("[Mm]on ", "votre ")
                .replaceAll("[Mm]a ", "votre ")
                .replaceAll("[Mm]es ", "vos ")
                .replaceAll("[Mm]oi ", "vous ");
        return processedText;
    }
}
