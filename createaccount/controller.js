import Ember from 'ember';

export default Ember.Controller.extend({
  actions: {
    signup: function() {
	  var createname = this.get('createname');
      var createusername = this.get('createusername');
      var createpassword = this.get('createpassword');
      let Createaccountdata = {
        'createusername' : createusername,
        'createpassword' : createpassword,
		'createname' : createname
    };
	var self=this;
     Ember.$.ajax({
          url:'/signup',
          type:'POST',
          data:Createaccountdata,
		  success:function(data){
			  search();
		  },
		  error:function(error){
			  alert(error);
		  }
      });
	  function search(){
			self.transitionToRoute('login');	  
		  }
    } 
  }
});
