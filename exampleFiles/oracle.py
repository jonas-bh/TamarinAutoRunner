#!/Users/finn/.pyenv/versions/2.7.18/bin/python2
# coding: latin-1

import re 
import sys

LINES = sys.stdin.readlines()
LEMMA = sys.argv[1]
L0, L1, L2, L3, L4, L5, L6, L7, L8 = [], [], [], [], [], [],[], [], []

if  LEMMA=="convs" or LEMMA=="lookA" or LEMMA=="readA" or LEMMA=="chatty": 
    for line in LINES:
        (num, val) = line.split(':')
        if "last(" in val :
            L1.append(num)
        elif "Pos(" in val:
            L0.append(num)
        elif "∥" in val:
            L2.append(num)
        elif "Sec_O(" in val :
            L3.append(num)
        elif "!KU(" in val:
            L4.append(num)
        else:
            L8.append(num)
elif LEMMA=="cashforvoucherNetto" : 
    for line in LINES:
        (num, val) = line.split(':')
        if "Cash(" in val :
            L1.append(num)
        elif "!Container(" in val:
            L2.append(num)
        elif "Sec_O(" in val :
            L3.append(num)
        elif "!KU(" in val:
            L4.append(num)
        elif "Info(" in val:
            L0.append(num)
        else:
            L8.append(num)
elif LEMMA=="cashforvoucherFix" or LEMMA=="cashforcontainerFix" or LEMMA=="cashforcontainerCustFix" or LEMMA=="cashforpurchaseFix" or LEMMA=="cashforcontainerCustKC"  or LEMMA=="cashforpurchaseCustFix":
    for line in LINES:
        (num, val) = line.split(':')
        if "Info(" in val :
            L0.append(num)
        elif "Cash(" in val:
            L1.append(num)
        elif "!KU(" in val:
            L2.append(num)
        elif "In_O(" in val :
            L3.append(num)
        elif "Out_I(" in val :
            L4.append(num)
        else:
            L8.append(num)

elif LEMMA=="cashforpurchaseCustNettoAttack" or LEMMA=="cashforpurchaseNetto" or LEMMA=="cashforcontainerCustKCattack" or LEMMA=="cashforpurchaseKCattack" or LEMMA=="cashforpurchaseNettoAttack" or LEMMA=="cashforcontainerCustNettoAttack" or LEMMA=="cashforpurchaseCustKCattack" or LEMMA=="cashforpurchaseKC" or LEMMA=="cashforcontainerCustNetto" or LEMMA=="cashforvoucherKC":
    for line in LINES:
        (num, val) = line.split(':')
        if "Pos(" in val :
            L1.append(num)
        elif "∥" in val:
            L0.append(num)
        elif "Out_OA(" in val:
            L1.append(num)
        elif "Cash(" in val :
            L2.append(num)
        elif "!Container(" in val:
            L4.append(num)
        elif "In_O(" in val:
            L5.append(num)
        elif "Info(" in val:
            L3.append(num)
        elif "!KU(" in val:
            L6.append(num)
        else:
            L8.append(num)

elif LEMMA=="cashforpurchaseCustNetto"  or  LEMMA=="cashforvoucherKCattack" or LEMMA=="cashforcontainerKCattack" : 
   for line in LINES:
        (num, val) = line.split(':')
        if "Pos(" in val :
            L1.append(num)
        elif "∥" in val:
            L2.append(num)
        elif "!KU(" in val :
            L0.append(num)
        elif "Cash(" in val :
            L4.append(num)
        elif "!Container(" in val:
            L5.append(num)
        elif "In_O(" in val:
            L6.append(num)
        elif "Info(" in val:
            L3.append(num)
        else:
            L8.append(num)
else:
    for line in LINES:
        (num, val) = line.split(':')
        if "Pos(" in val :
            L6.append(num)
        elif "Out_OA(" in val or "Chatty(" in val :
            L0.append(num)
        elif "!KU(" in val :
            L2.append(num)
        elif "Cash(" in val :
            L4.append(num)
        elif "!Container(" in val:
            L3.append(num)
        elif "In_O(" in val:
            L5.append(num)
        elif "Info(" in val:
            L1.append(num)
        else:
            L8.append(num)

for i in L0 + L1 + L2 + L3 + L4 + L5 + L6 + L7+ L8:
    print(i)



    
