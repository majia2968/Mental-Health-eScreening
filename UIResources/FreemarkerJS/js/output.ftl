<#include "clinicalnotefunctions">
${TITLE_START}
DEMOGRAPHICS and SOCIAL:
${TITLE_END}${SECTION_START}
<#assign Section: section3>
    The Veteran is a ${ getFormulaDisplayText(var12)} year-old<#t>
    <#if !(hasValue(getSelectOneDisplayText(var30))) || wasAnswerNone(var30)> . <#t>
    <#else> ${ ""?left_pad(1)}whom is ${ getSelectOneDisplayText(var30)}. <#t>
    <#-- The Veteran reports being a White/Caucasian, American Indian or Alaskan Native male. -->
    <#if hasValue(getSelectMultiDisplayText(var40)) || hasValue(getSelectOneDisplayText(var20)) >
        The Veteran reports being<#t>
        <#if hasValue(getSelectMultiDisplayText(var40)) > ${ ""?left_pad(1)}a ${ getSelectMultiDisplayText(var40)}<#t>
            <#if hasValue(getSelectOneDisplayText(var20)) > ${ ""?left_pad(1)}${ getSelectOneDisplayText(var20)}<#t>. <#t>
            <#-- The Veteran reported completing some high school. -->
             <#if hasValue(getSelectOneDisplayText(var50)) > The Veteran reported completing ${ getSelectOneDisplayText(var50)}. <#t>
                <#-- The Veteran reported being currently an employed, who usually works as retail. -->
                <#if hasValue(getSelectOneDisplayText(var60)) && hasValue(getSelectOneDisplayText(var70)) >
                     The Veteran reported being currently ${ getSelectOneDisplayText(var60)}, who usually works as an ${ getAnswerDisplayText(var70)}. <#t>
                <#elseif hasValue(getSelectOneDisplayText(var60)) && !(hasValue(getSelectOneDisplayText(var70)))>
                    The Veteran reported being currently ${ getSelectOneDisplayText(var60)}. <#t>
                <#elseif !(hasValue(getSelectOneDisplayText(var60))) && hasValue(getSelectOneDisplayText(var70)) >
                    The Veteran reported usually working as an ${ getAnswerDisplayText(var70)}. <#t>
                    <#--The Veteran reported that the primary source of income is work, and disability. -->
                    <#if hasValue(getAnswerDisplayText(var80)) > The Veteran reported that <#t><#if wasAnswerNone(var80)> they do not have any income. <#t><#else> their income is derived from ${ getSelectMultiDisplayText(var80)}. <#t><#--The Veteran is married. --><#if hasValue(getSelectOneDisplayText(var100)) > The Veteran is ${ getSelectOneDisplayText(var100)}. <#t><#--The Veteran lives in a house with spouse or partner, and children. --><#if hasValue(getSelectOneDisplayText(var110)) && hasValue(getSelectMultiDisplayText(var90)) > The Veteran ${ getSelectOneDisplayText(var110)} <#t><#if wasAnswerNone(var90)> alone. <#t><#else> with ${ getSelectMultiDisplayText(var90)}. <#t><#elseif hasValue(getSelectOneDisplayText(var110)) && !(hasValue(getSelectMultiDisplayText(var90)))>The Veteran ${ getSelectOneDisplayText(var110)}. <#t><#elseif !(hasValue(getSelectOneDisplayText(var110))) && hasValue(getSelectMultiDisplayText(var90)) >The Veteran lives with ${ getSelectMultiDisplayText(var90)}. <#t><#--The Veteran reported not having any children. --><#if hasValue(getAnswerDisplayText(var131)) > <#if wasAnswerTrue(var131) > ${ getAnswerDisplayText(var131)} <#t><#else> <#--The Veteran has 2 child(ren) who are child(ren) are 2,4 years old. --><#if !(getNumberOfTableResponseRows(var130) = -1) > <#if (getNumberOfTableResponseRows(var130) = 0) > The Veteran reported not having any children. <#t><#elseif (getNumberOfTableResponseRows(var130) = 1) >The Veteran has 1 child who is ${ getTableMeasureDisplayText(var130)} years old. <#t><#elseif (getNumberOfTableResponseRows(var130) > 1) >The Veteran has ${ getNumberOfTableResponseRows(var130)} children who are ${ getTableMeasureDisplayText(var130)} years old. <#t><#--The Veteran reported BMI is 27, indicating that he/she may be is overweight. --><#if hasValue(getFormulaDisplayText(var11)) > The Veteran's reported BMI is ${ num}, indicating that he/she <#t><#if (num < 18. > 5) is below a normal a weight. <#t><#elseif (num < 25) && (num >= 18. 5) >is at a normal weight. <#t><#elseif (num < 30) && (num >= 25) >is overweight. <#t><#elseif (num < 40) && (num >= 30) >is obese. <#t><#elseif (num >= 40) >is morbid obese. <#t><#if !(section3 = '') > ${ section3}<#else> ${ noParagraphData}${SECTION_END}