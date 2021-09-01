import Ember from 'ember';

export default Ember.Controller.extend({
	actions: {
		logout : function(){
			
			$("#whole").css("margin-top", "0px");
			this.transitionToRoute('login');
		},
		autoassign : function (){
			var d=new Date();
			var m = d.getMinutes();
			var h = d.getHours();
			var taskname = this.get('taskname');
      var taskdescription = this.get('taskdescription');
	  var taskestitime = this.get('taskestitime');
	  var tasktype=$('#tasktype').val();
	  let taskdata1 = {
        'taskname' : taskname,
        'taskdescription' : taskdescription,
		'taskestitime' : taskestitime,
		'tasktype' : tasktype
    };
	 var s1="false";
	 var self=this;
	Ember.$.ajax({
          url:'/autoassign',
          type:'POST',
		  dataType:'json',
          data:taskdata1,
		  success:function(data){
			  console.log(data);
			 var datastring=$.parseJSON(JSON.stringify(data));
			 var res=datastring[0].result;
			 var cname=datastring[0].assigntoo;
			 var delay=datastring[0].delay;
			 var delayday=datastring[0].delaydate;
			 var flagtime=datastring[0].flagtime;
			 if(flagtime==0){
				  alert("task will be autoassigned to "+cname+" on "+delayday+" at "+h+":"+m+" hours");
			 }else{
			 alert("task will be autoassigned to "+cname+" in "+delay+" hours on "+delayday+" at 9:00 hours");
			 }
			 var res1 = $.trim(res);
			 search2();
		  },
		  error:function(error){
			  alert(error);
		  }
      });
	  function search2(){
			  $("#whole").css("margin-top", "-155px");
			self.transitionToRoute('tasklist');	  
		  }
			
		},
	assignto: function() {
		var d=new Date();
			var m = d.getMinutes();
			var h = d.getHours();
			alert(h);
			if(h>17){
				alert("You cant assign task after 6:00pm");
			}
			else{
				alert("after 6");
		Ember.$.ajax({
			url:'/assign',
			type:'GET',
			dataType:'json',
			success:function(data){		
				var datastring=$.parseJSON(JSON.stringify(data));
				console.log(datastring);
				var student=' ';
				student += '<select id="userassign">';
				$(datastring).each(function (index, item){
					var name=item.username;
					student += '<option value="'+name+'">';
					student += name;
					student += '</option>';
				});
				student += '</select>';
				$('#assignuser').append(student);
			},
			error:function(error){
			  alert(error);
		    }
		});
			}
	},
    createtask: function() {
		var d=new Date();
			var m = d.getMinutes();
			var h = d.getHours();
			if(h>17){
				alert("You cant assign task after 6:00pm");
			}
			else{
      var taskname = this.get('taskname');
      var taskdescription = this.get('taskdescription');
	  var taskestitime = this.get('taskestitime');
	  var taskassign = $('#userassign').val();
      let taskdata = {
        'taskname' : taskname,
        'taskdescription' : taskdescription,
		'taskestitime' : taskestitime,
		'taskassign' : taskassign,
    };
	 var s1="false";
	 var self=this;
	Ember.$.ajax({
          url:'/addtask',
          type:'POST',
		  dataType:'text',
          data:taskdata,
		  success:function(data){
			 var datastring=$.parseJSON(JSON.stringify(data));
			 var res=datastring[0].result;
			 var res1 = $.trim(res);
			 search1();
		  },
		  error:function(error){
			  alert(error);
		  }
      });
	  function search1(){
			  $("#whole").css("margin-top", "-155px");
			self.transitionToRoute('tasklist');	  
		  }
	}
    }
    	
  }
});

