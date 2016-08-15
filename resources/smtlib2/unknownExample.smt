(set-option :auto-config false)
(set-option :smt.mbqi false)

(declare-sort T6)
(declare-sort T7)
(declare-fun set23 (T7 T7) Bool)
(assert
    (forall
        (
            (bv1 T7)
            (bv0 T7)
        )
        (=
            (set23 bv0 bv1)
            (= bv0 bv1)
        )
    )
)
(check-sat)
