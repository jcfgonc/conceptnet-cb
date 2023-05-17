# Introduction

This repository contains a customized version of the semantic network/knowledge graph/base [ConceptNet V5](https://conceptnet.io) used by me and by my colleagues during my PhD in Computational Creativity .

The graph itself is stored inside the **kb** folder as a straightforward parsable CSV (comma separated value) text file. Each line/row of the file is in the format source,relation,target (e.g., subject-verb-object style).

The majority of customization was in removing stuff I don't need for my research projects (in Conceptual Blending Theory and Computational Analogy) such as:
- non-English facts
- many biased and controversial facts, such as political and gender issues, sexist remarks, funny (but wrong) statements and plainly wrong facts
- facts about cities, towns, etc. (not of much use for computational creativity)

I also added various facts about entities (animals, people, etc.) while I was doing my experiments and that I don't remember now.

The folder **src** contains code I use to study/manipulate ConceptNet. Probably not of much use to anyone else.

If you need to cite this work please use the following paper:

*J. Gonçalves, P. Martins, and A. Cardoso, “A Large-Scale Computational Model of Conceptual Blending Using Multiple Objective Optimisation,” in Proceedings of the 12th International Conference on Computational Creativity (ICCC 2021), 2021-09-14, pp. 424–428.*

# Licensing

This work is released under the CC BY-NC-SA license, a copy of which is included in this
directory.

# People

I, João Gonçalves, am the only contributor to this work.

Please email jcfgonc@gmail.com with questions, comments, and bug reports.

# Acknowledgements

This work has partly been funded by national funds through the FCT - Foundation 
for Science and Technology, I.P., within the scope of the project 
CISUC - UID/CEC/00326/2020 and by European Social Fund, through the 
Regional Operational Program Centro 2020.
