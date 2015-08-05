$(document).ready(function() {
	DrawCaptcha();
	$('#goButton').click(function() {
		ValidCaptcha();
		});
});

   //Created / Generates the captcha function    
    function DrawCaptcha()
    {
        var a = Math.ceil(Math.random() * 10)+ '';
        var b = Math.ceil(Math.random() * 10)+ '';       
        var c = Math.ceil(Math.random() * 10)+ '';  
        var d = Math.ceil(Math.random() * 10)+ '';  
      
        var code = a + ' ' + b + ' ' + ' ' + c + ' ' + d;
        document.getElementById("txtCaptcha").value = code;
    }

    // Validate the Entered input aganist the generated security code function   
    function ValidCaptcha(id){
        var str1 = removeSpaces(document.getElementById('txtCaptcha').value);
        var str2 = removeSpaces(document.getElementById('TextinBoxatRight').value);
        if (str1 == str2) return true;
        document.getElementById(id).innerText = "Incorrected Captcha";
        document.getElementById(id).style.display = "";
        return false;
        
    }

    // Remove the spaces from the entered and generated code
    function removeSpaces(string)
    {
        return string.split(' ').join('');
    }
    
