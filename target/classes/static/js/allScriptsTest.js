function check() {
    var submit = document.getElementsByName('submit')[0];
    var notChecked = false;
    if (!document.getElementById('politics1').checked) notChecked = true;
    // if (!document.getElementById('politics2').checked) notChecked = true;
    if(!notChecked) submit.disabled = '';
    else submit.disabled = 'disabled';
}


function show_hide_password(target){
    var input = document.getElementById('password-input');

    if (input.getAttribute('type') == 'password') {
        target.classList.add('view');
        input.setAttribute('type', 'text');
    }else {
        target.classList.remove('view');
        input.setAttribute('type', 'password');
    }
    return false;
}


// /* В форме авторизация в поле password-input можно посмотреть пароль homeHTML/authorization */
//
// function show_hide_password(target){
//     var input = document.getElementById('password-input');
//
//     if (input.getAttribute('type') == 'password') {
//         target.classList.add('view');
//         input.setAttribute('type', 'text');
//     }else {
//         target.classList.remove('view');
//         input.setAttribute('type', 'password');
//     }
//     return false;
// }
//
// /* Скрипты для администратора */
// /* В форме добавления аккаунта в поле password можно посмотреть пароль adminHTML/admin-account */
//
// function show_hide_account_password(target){
//     var input = document.getElementById('password');
//
//     if (input.getAttribute('type') == 'password') {
//         target.classList.add('view');
//         input.setAttribute('type', 'text');
//     }else {
//         target.classList.remove('view');
//         input.setAttribute('type', 'password');
//     }
//     return false;
// }
//
// /* Кнопка доступна, если все поля заполнены в форме для добавления нового сотрудника adminHTML/admin.html*/
//
// function checkParamsAddNewStaff() {
//     var addLname = $('#addLname').val();
//     var addFname = $('#addFname').val();
//     var addPname = $('#addPname').val();
//     var addPosition = $('#addPosition').val();
//
//     if(addLname.trim().length !== 0 && addFname.trim().length !== 0 && addPname.trim().length !== 0 && addPosition.trim().length !== 0  ) {
//         $('#btnAddNewStaff').removeAttr('disabled');
//     }else {
//         $('#btnAddNewStaff').attr('disabled', 'disabled');
//     }
// }
//
// /* Кнопка доступна, если все поля заполнены в форме редактирования сотрудника adminHTML/staffEdit.html*/
//
// function checkParamsUpdateStaffInAdmin() {
//     var lname = $('#lname').val();
//     var fname = $('#fname').val();
//     var pname = $('#pname').val();
//     var position = $('#position').val();
//
//     if(lname.trim().length !== 0 && fname.trim().length !== 0 && fname.trim().length !== 0 && position.trim().length !== 0  ) {
//         $('#btnUpdateStaff').removeAttr('disabled');
//     }else {
//         $('#btnUpdateStaff').attr('disabled', 'disabled');
//     }
// }
//
// /* Кнопка доступна, если все поля заполнены в форме для написания заявки adminHTML/request.html*/
// function checkParams() {
//     var level = $('#level').val();
//     var fromWhom = $('#fromWhom').val();
//     var toWhom = $('#toWhom').val();
//     var text = $('#text').val();
//
//     if(level.trim().length !== 0 && fromWhom.trim().length !== 0 && toWhom.trim().length !== 0 && text.trim().length !== 0  ) {
//         $('#submit').removeAttr('disabled');
//     }else {
//         $('#submit').attr('disabled', 'disabled');
//     }
// }
//
// /* Кнопка доступна, если все поля ответа на заявку заполнены adminHTML/r equestEdit.html */
//
// function checkParamsEdit () {
//     var roomEdit = $('#roomEdit').val();
//     var levelEdit = $('#levelEdit').val();
//     var fromWhomEdit = $('#fromWhomEdit').val();
//     var toWhomEdit = $('#toWhomEdit').val();
//     var statusEdit = $('#statusEdit').val();
//     var nameEdit = $('#nameEdit').val();
//     var textEdit = $('#textEdit').val();
//     var CommentEdit = $('#CommentEdit').val();
//
//     if(roomEdit.trim().length !== 0 && levelEdit.trim().length !== 0 && fromWhomEdit.trim().length !== 0 && toWhomEdit.trim().length !== 0 && statusEdit.trim().length !== 0  &&
//         nameEdit.trim().length !== 0 && textEdit.trim().length !== 0 && CommentEdit.trim().length !== 0) {
//         $('#submitEdit').removeAttr('disabled');
//     }else {
//         $('#submitEdit').attr('disabled', 'disabled');
//     }
// }
//
// /* Кнопка доступна, если все поля ответа на заявку заполнены adminHTML/requestReply.html */
//
// function checkParamsReply () {
//     var statusReply = $('#statusReply').val();
//     var nameReply = $('#nameReply').val();
//
//     if(statusReply.trim().length !== 0 && nameReply.trim().length !== 0) {
//         $('#submitReply').removeAttr('disabled');
//     }else {
//         $('#submitReply').attr('disabled', 'disabled');
//     }
// }
//
// /* Кнопка доступна, если все поля заполнены в форме для добавления новой должности account.html*/
//
// function checkParamsAddAccount() {
//     var username = $('#username').val();
//     var password = $('#password').val();
//     var roles = $('#roles').val();
//
//     if(username.trim().length !== 0 && password.trim().length !== 0 && roles.trim().length !== 0 ) {
//         $('#btnAddAccount').removeAttr('disabled');
//     }else {
//         $('#btnAddAccount').attr('disabled', 'disabled');
//     }
// }
//
// /* Скрипты для аккаунтов сотрудников */
// /* Кнопка доступна, если все поля заполнены в форме для написания заявки staffHTML/request.html*/
//
// function checkParamsRequestForStaff() {
//     var level = $('#level').val();
//     var fromWhom = $('#fromWhom').val();
//     var toWhom = $('#toWhom').val();
//     var text = $('#text').val();
//
//     if(level.trim().length !== 0 && fromWhom.trim().length !== 0 && toWhom.trim().length !== 0 && text.trim().length !== 0  ) {
//         $('#submit').removeAttr('disabled');
//     }else {
//         $('#submit').attr('disabled', 'disabled');
//     }
// }
//
// /* Кнопка доступна, если все поля ответа на заявку заполнены staffHTML/requestReply.html */
// function checkParamsRequestEditForStaff () {
//     var statusEditStaff = $('#statusEditStaff').val();
//     var nameEditStaff = $('#nameEditStaff').val();
//
//     if(statusEditStaff.trim().length !== 0 && nameEditStaff.trim().length !== 0) {
//         $('#submitEditStaff').removeAttr('disabled');
//     }else {
//         $('#submitEditStaff').attr('disabled', 'disabled');
//     }
// }
//
// /* В поле room можно вводить тоьлко цифры adminHTML/request.html и staffHTML/request.html */
//
// function validator(evt) {
//     var theEvent = evt || window.event;
//     var key = theEvent.keyCode || theEvent.which;
//     key = String.fromCharCode( key );
//     var regex = /[0-9]|\./;
//     if( !regex.test(key) ) {
//         theEvent.returnValue = false;
//         if(theEvent.preventDefault) theEvent.preventDefault();
//     }
// }