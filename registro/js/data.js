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



$('#guardar').click(function () {
    var database = firebase.database();

    if($("rol").val()==1){
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
    firebase.auth().signInAnonymously().then(
        user=>{
            firebase.database().ref(email).push({
                name,
                email,
                pass

            });
        }

        )
}


function send(name,carrera,grupo,docente,ncontrol,pass) {
    firebase.auth().signInAnonymously().then(
        user=>{
            firebase.database().ref(ncontrol).push({
                name,
                carrera,
                grupo,
                docente,
                ncontrol,
                pass

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
    $('#guardarAlumno').attr('hidden','hidden');
    $("#rol").val(2);


    $('.nombre').removeAttr('hidden');
    $('.correo').removeAttr('hidden');
    $('.password').removeAttr('hidden');
    $('#guardarMaestro').removeAttr('hidden');
});

$('#alumno').on('click',function () {
    $('.correo').attr('hidden','hidden');
    $('#guardarMaestro').attr('hidden','hidden');
    $("#rol").val(1);
    $('.nombre').removeAttr('hidden');
    $('.ncontrol').removeAttr('hidden');
    $('.carrera').removeAttr('hidden');
    $('.grupo').removeAttr('hidden');
    $('.docente').removeAttr('hidden');
    $('.password').removeAttr('hidden');
    $('#guardarAlumno').removeAttr('hidden');
});