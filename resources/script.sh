#!/usr/bin/env bash
outputs=$(pwd)"/outputs"
for sut in $(ls $outputs);
do
    sut=$outputs/$sut
    for preds in $(ls $sut);
    do
        preds=$sut/$preds
        for dotFile in $(ls $preds/*.dot);
        do
            pdfFile=$preds/$(basename $dotFile .dot).pdf
            echo $pdfFile
            dot -Tpdf $dotFile >$pdfFile
        done
    done;
done;
