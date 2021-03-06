$( document ).ready(function() {
 show();
});



// Initialize Firebase
var config = {
    apiKey: "AIzaSyD28Xc3w4a-Yy228rOTw6jbQbqz00b0U2I",
    authDomain: "escuela-2a8e4.firebaseapp.com",
    databaseURL: "https://escuela-2a8e4.firebaseio.com",
    projectId: "escuela-2a8e4",
    storageBucket: "escuela-2a8e4.appspot.com",
    messagingSenderId: "577422151155"
};
firebase.initializeApp(config);
var bd = firebase.database().ref('users');
bd.orderByKey().on("child_added", function(snapshot) {
    var us = snapshot.val();
    console.log(us.name);
    console.log(us.ncontrol);


});



$('#guardar').click(function () {
    var database = firebase.database();

    if($("#rol").val()==1){
    var name =$('#nombre').val();
    var carrera =$('#carrera').val();
    var grupo = $('#grupo').val();
    var docente = $('#docente').val();
    var pass = $('#pass').val();
    var ncontrol = $('#ncontrol').val();
    var band = '';


    if(name != ''){
        band ++;
    }
    if(carrera != ''){
        band ++;
    }
    if(grupo != ''){
        band ++;
    }
    if(docente != ''){
        band ++;
    }
    if(pass != ''){
        band ++;
    }
    if(ncontrol != ''){
        band ++;
    }

    if(band == 6){
        send(name,carrera,grupo,docente,ncontrol,pass);
        alert('Se registró el alumno');
        $('#nombre').val('');
        $('#carrera').val('');
        $('#grupo').val('');
        $('#docente').val('');
        $('#pass').val('');
        $('#ncontrol').val('');
    }else{
        alert('todos los campos son obligatorios');
        console.log(band);
    }
}else{
     var name =$('#nombre').val();
    var pass = $('#pass').val();
    var email= $('#correo').val();
    if(name != ''&&pass != ''&&email != ''){
         sendDocente(name,email,pass);
        alert('Se registró el Docente');
       
    }else{
         alert('todos los campos son obligatorios');
    }
   
   

}
});
function sendDocente(name,email,pass) {
    var rol=$("#rol").val();
    firebase.auth().signInAnonymously().then(
        user=>{
            firebase.database().ref("users").push({
                name,
                email,
                pass,
                rol 


            });
        }

        )
}


function send(name,carrera,grupo,docente,ncontrol,pass) {
    var rol=$("#rol").val();
    firebase.auth().signInAnonymously().then(
        user=>{
            firebase.database().ref("users").push({
                name,
                carrera,
                grupo,
                docente,
                ncontrol,
                pass,
                rol

            });
        }

        )
}

$('#show').click(function () {
 show();
});

function show() {
    var data = firebase.database().ref('alumnos');
    data.on('child_added', function (snapshot) {
        $('#nam').append('<tr>');
        $('#nam').append('<td>'+snapshot.val().name+'</td>');
        $('#nam').append('<td>'+snapshot.val().email+'</td>');
        $('#nam').append('<td>'+snapshot.val().phone+'</td>');
        $('#nam').append('</tr>');


    })

}

function isNumberKey(evt)
{
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}

$('#maestro').on('click',function () {
    $('.ncontrol').attr('hidden','hidden');
    $('.grupo').attr('hidden','hidden');
    $('.docente').attr('hidden','hidden');
    $('.carrera').attr('hidden','hidden');

    $("#rol").val(2);


    $('.nombre').removeAttr('hidden');
    $('.correo').removeAttr('hidden');
    $('.password').removeAttr('hidden');
    $('.guardar').removeAttr('hidden');
    $("#guardar").html("Registrar Docente");
});

$('#alumno').on('click',function () {
    $('.correo').attr('hidden','hidden');

    $("#rol").val(1);
    $('.nombre').removeAttr('hidden');
    $('.ncontrol').removeAttr('hidden');
    $('.carrera').removeAttr('hidden');
    $('.grupo').removeAttr('hidden');
    $('.docente').removeAttr('hidden');
    $('.password').removeAttr('hidden');
    $('.guardar').removeAttr('hidden');
    $("#guardar").html("Registrar Alumno");
});

function login(user, password) {
    var data = firebase.database().ref('users');
    data.orderByKey().equalTo(user).on("child_added", function(snapshot) {
        var us = snapshot.val();
        console.log(us.name);


    });

}