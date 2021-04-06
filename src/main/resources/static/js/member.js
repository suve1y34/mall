function chkItem(ele, nm) {
    if(!ele.value) {
        alert(`${nm}을(를) 작성해 주세요.`);
        ele.focus();
        return true;
    }
}

var joinBtnElem = document.querySelector('#joinBtn')
if(joinBtnElem) {
    var frmElem = document.querySelector('#joinForm')
    var userIdElem = frmElem.mem_id
    var userEmailElem = frmElem.email
    var userNmElem = frmElem.nm
    var userPwElem = frmElem.pw
    var userPwReElem = frmElem.memberPwRe
    var userPhoneElem = frmElem.phone
    var userPostCodeElem = frmElem.postCode
    var userAddressElem = frmElem.address
    var userDeAddressElem = frmElem.de_address

    function eleChk () { //이상있으면 true, 이상없으면 false
        if(chkItem(userIdElem, '아이디') || chkItem(userEmailElem, '이메일') || chkItem(userNmElem, '이름') || chkItem(userPwElem, '비밀번호')
        || chkItem(userPhoneElem, '연락처') || chkItem(userPostCodeElem, '주소')) {
            return true
        } else if(userPwElem.value !== userPwReElem.value) {
            alert('비밀번호를 확인해 주세요.');
            userPwElem.focus()
            return true
        }
        return false
    }

    function ajax () {
        var param = {
            mem_id: userIdElem.value,
            email: userEmailElem.value,
            nm: userNmElem.value,
            pw: userPwElem.value,
            phone: userPhoneElem.value,
            postCode: userPostCodeElem.value,
            address: userAddressElem.value,
            de_address: userDeAddressElem.value
        }

        fetch('/join', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(param)
        }).then(function(res) {
            return res.json()
        }).then(function(myJson) {
            proc(myJson)
        })
    }

    function proc (myJson) {
        if(myJson.result === 0) { //회원가입 실패
            alert('회원가입에 실패하였습니다.')
            return
        } else if(myJson.result === 1) {
            alert('회원가입을 축하합니다')
            location.href = '/user/signin'
        }

    }

    joinBtnElem.addEventListener('click', function() {
        if(eleChk()) { return }
        ajax()
    })

}

var loginBtnElem = document.querySelector('#loginBtn')
if(loginBtnElem) {
    var frmElem = document.querySelector('#loginForm')
    var userIdElem = frmElem.mem_id
    var userPwElem = frmElem.pw
    var errMsgElem = document.querySelector('#errMsg')

    function ajax() {
        if(userIdElem.value === '') {
            alert('아이디를 입력해 주세요')
            return
        } else if(userPwElem.value === '') {
            alert('비밀번호를 입력해 주세요')
            return
        }
        var param = {
            mem_id: userIdElem.value,
            pw: userPwElem.value
        }

        fetch('/login', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(param)
        }).then(function(res) {
            return res.json()
        }).then(function(myJson) {
            proc(myJson)
        })
    }

    function proc (myJson) {
        switch(myJson.result) {
            case 1:
                location.href = '/'
                return
            case 2:
                alert('아이디를 확인해 주세요.')
                return
            case 3:
                alert('비밀번호를 확인해 주세요.')
                return
        }
    }
    loginBtnElem.addEventListener('click', ajax)
}


//아이디 체크
var chkIdBtnElem = document.querySelector('#id_btn')
if(chkIdBtnElem) {

    function ajax () {
        var frmElem = document.querySelector('#joinForm')
        var userIdElem = frmElem.mem_id
        var userIdValue = userIdElem.value
        var idChkMsgElem = frmElem.querySelector('#idChkResult')

        fetch(`/chkId/${userIdValue}`)
            .then(function (res) {
                return res.json()
            })
            .then(function(myJson) {
                console.log(myJson);
                if(myJson.result === 1) {
                    idChkMsgElem.innerText = '중복된 아이디가 있습니다.';
                } else {
                    idChkMsgElem.innerText = '사용할 수 있는 아이디 입니다.';
                }
            })
    }

    chkIdBtnElem.addEventListener('click', ajax)
}

//비밀번호 찾기
var findPwForm = document.querySelector('#findPwForm');
var memId = findPwForm.mem_id;
var memEmail = findPwForm.pw;

function chkInfoForm(){
    if(chkItem(memId, '아이디') || chkItem(memEmail, '이메일')) {
        return true;
    }
    return false;
}

var changePwForm = document.querySelector('#changePwForm');
var pw = changePwForm.pw
var pwRe = changePwForm.pw_re

function chkPwForm(){
    if(chkItem(pw, '비밀번호') || chkItem(pwRe, '비밀번호 확인')) {
        return true;
    } else if(pw.value !== pwRe.value) {
        alert('비밀번호를 확인해 주세요.');
        pw.focus();
        return true
    }
    return false
}
