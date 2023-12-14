package thirdLab;

public enum CntOfRounds {
    //permutation->gamming->simpleSubstitution->gamming->permutation
    FIVE_PE,
    //simpleSubstitution->gamming->permutation->gamming->simpleSubstitution
    FIVE_SU,
    //permutation->gamming->simpleSubstitution->gamming
    //->permutation->gamming->simpleSubstitution->gamming->permutation
    NINE_PE,
    //simpleSubstitution->gamming->permutation->gamming
    //->simpleSubstitution->gamming->permutation->gamming->simpleSubstitution
    NINE_SU;
}
