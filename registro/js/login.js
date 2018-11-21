$( document ).ready(function() {
    show();
 });
 
 var config = {
    apiKey: "AIzaSyD1R_PTeK1xXC5VA8K7g9cd9f2tvSWyieo",
    authDomain: "bitacora2-fda9b.firebaseapp.com",
    databaseURL: "https://bitacora2-fda9b.firebaseio.com",
    projectId: "bitacora2-fda9b",
    storageBucket: "",
    messagingSenderId: "61850780719"
  };
  firebase.initializeApp(config);
 
 $('#validar').click(function () {
     
    if(name == 'wicho'){
     var name =$('#inputEmail').val();
     var email =$('#inputPassword').val();
     
    }
 
    
 });

 $('#guardar').click(function () {
     try{
    var name =$('#nombre').val();
    var carrera =$('#carrera').val();
    var grupo = $('#grupo').val();
    var  ndasignado= $('#ndasignado').val();
    var date = $('#date').val();

    var database = firebase.database();

    send(name,carrera,grupo,ndasignado,date);
    // $('#name').val('');
    // $('#email').val('');
    // $('#phone').val('');
}catch(Exception ){
    alert("no se ha podido guardar la info");

}
});
 
 function send(name,email,phone) {
     firebase.auth().signInAnonymously().then(
         user=>{
             firebase.database().ref('users').push({
                 name,
                 email,
                 phone
             });
         }
 
     )
 }
 
 $('#show').click(function () {
    show();
 });
 
 function show() {
     var data = firebase.database().ref('users');
     data.on('child_added', function (snapshot) {
         $('#nam').append('<tr>');
         $('#nam').append('<td>'+snapshot.val().name+'</td>');
         $('#nam').append('<td>'+snapshot.val().email+'</td>');
         $('#nam').append('<td>'+snapshot.val().phone+'</td>');
         $('#nam').append('</tr>');
 
 
     })
 
 }