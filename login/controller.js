import Ember from 'ember';

export default Ember.Controller.extend({
	
	d:'lo',
	actions: {
	fetch : function(){
		Ember.$.ajax({
			url:'/fetch',
			type:'GET',
			dataType:'json',
			 success:function(data){
				var datastring=$.parseJSON(JSON.stringify(data));
			 var res=datastring[0].result;
			 var res1 = $.trim(res);
			 if(res1=="true"){
				 var uname=datastring[0].uname;
				 var uname1 = $.trim(uname);
				 var upass=datastring[0].upass;
				 var upass1 = $.trim(upass);
				 $('#luname').val(uname1);
				 $('#lupass').val(upass1);
			 }
			 else{
				 alert("Please login");
			 }
			},
			error: function(error){
				alert("error");
			}
		});
	},
    login: function() {
      var loginUsername = $('#luname').val();
      var loginPassword = $('#lupass').val();
	var check=$("#check").prop('checked')	
      let Loginaccountdata = {
        'loginUsername' : loginUsername,
        'loginPassword' : loginPassword,
		'remember' : check
    };
	var s="false";
	var self=this;
    Ember.$.ajax({
          url:'/login',
          type:'POST',
		  dataType:'json',
          data:Loginaccountdata,
		  success:function(data){
			  var datastring=$.parseJSON(JSON.stringify(data));
			  var loginusername = datastring[0].loginusername;
               var loginpassword = datastring[0].loginpassword;
               var res = datastring[0].result;
			   var result = $.trim(res);
			   
			   $('.loginresponse').html(result);
			  const s1= result;
			  const s2="Successfullogin";
			  const s3="Userinvalid";
			  const s4="Passinvalid";
			  s="true";
			  if(s1==s2)
			  {
				  search();
			  }
		  },
		   error:function(error){
			  alert(error);
		  }
      });
	  	  function search(){
			  $("#whole").css("margin-top", "-155px");
			self.transitionToRoute('tasklist');	  
		  }

    }

  }

});

