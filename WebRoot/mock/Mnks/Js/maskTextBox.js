function MaskTextBox(document, textBoxId, hiddenMaskId) {
    this.onKeyUp = onKeyUp;
    this.onBlur = onBlur;
    this.setCaretPos = setCaretPos;
    var prev = "";
    var caretPos = 0;
    var start = 0;
    var end = 0;
    var maxLen = 0;
    var textBox = document.getElementById(textBoxId);
    if (textBox != null)
        maxLen = textBox.getAttribute("maxlength");
    var arr;
    var sepIndexArray;
    var sepRemoveRegEx;
    var hiddenMask = document.getElementById(hiddenMaskId);
    if (hiddenMask != null) {
        arr = hiddenMask.value.split("||");
        if (arr != null && arr.length == 2) {
            sepIndexArray = arr[0].split("|");
            sepRemoveRegEx = new RegExp(arr[1], "g");
        }
    }
    function onBlur(o, e) {
        o.value = reformat(o.value, maxLen);
    }
    function onKeyUp(o) {
        var ret = setCaretPos(o);
        if (o.value != prev) {
            o.value = reformat(o.value, maxLen);
            caretPos = offsetCarretPos(o);
            prev = o.value;
            setRange(o, caretPos);
            ret = setCaretPos(o);
        }
        return ret;
    }
    function reformat(val, len) {
        val = insertSeparators(removeSeparators(val));
        if (val.length > len)
            val = val.substr(0, len);
        return val;
    }
    function insertSeparators(val) {
        var sep, sepIndexPair, sepIndex = 0;
        for (var i = 0; i < sepIndexArray.length; ++i) {
            sepIndexPair = sepIndexArray[i].split("!");
            sepIndex = sepIndexPair[1];
            if (val.length > sepIndex) {
                sep = sepIndexPair[0];
                if (sep != val.substr(sepIndex, 1))
                    val = val.substr(0, sepIndex) + sep + val.substr(sepIndex, val.length - sepIndex);
            }
            else
                break;
        }
        return val;
    }
    function removeSeparators(val) {
        return val.replace(sepRemoveRegEx, "");
    }
    function offsetCarretPos(o) {
        var caret = caretPos;
        var offset = 0;
        var valLen = o.value.length
        var prevLen = prev.length
        if (valLen > prevLen) {
            if (caret > prevLen)
                caret = valLen;
            else {
                var sep;
                for (var i = 0; i < sepIndexArray.length; ++i) {
                    sepIndexPair = sepIndexArray[i].split("!");
                    sep = sepIndexPair[0];
                    if (o.value.substr(caretPos + offset - 1, 1) == sep) {
                        offset = (valLen - prevLen) > 1 ? 1 : 0;
                        offset = offset + 1;
                    }
                }
                offset = (offset > -1 ? offset : 0);
            }
        }
        return caret + offset;
    }
    function setRange(o) {
        if (o.value.length > 0) {
            if (o.createTextRange) {
                var txRange = o.createTextRange();
                txRange.move("character", caretPos);
                txRange.select();
            }
            else o.setSelectionRange(caretPos, caretPos);
        }
    }
    function trimCaretPos() {
        if (caretPos < 0) caretPos = 0;
        else if (caretPos > maxLen) caretPos = maxLen;
    }
    function setCaretPos(o) {
        var s = getSelStart(o), e = getSelEnd(o);
        if (s == start && e == end)
            return false;
        caretPos = s;
        start = s;
        end = e;
        return true;
    }
    function getSelStart(o) {
        if (o.createTextRange) {
            var r = document.selection.createRange().duplicate();
            r.moveEnd('character', o.value.length);
            if (r.text == '')
                return o.value.length;
            return o.value.lastIndexOf(r.text);
        }
        else return o.selectionStart;
    }
    function getSelEnd(o) {
        if (o.createTextRange) {
            var r = document.selection.createRange().duplicate();
            r.moveStart('character', -o.value.length);
            return r.text.length;
        }
        else return o.selectionEnd;
    }
}