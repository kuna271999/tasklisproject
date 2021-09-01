import Ember from 'ember';
export default Ember.Controller.extend({
	actions: {
		report : function(){
					var selmethod=$('#selectmethod').val();
		var self=this;
     Ember.$.ajax({
          url:'/viewtask',
          type:'GET',
		  data:{'selmethod':selmethod},
		  dataType:'json',
		  success:function(data){
			  $('#tasktable').empty();
			  $('#welcome').empty();
			 var datastring=$.parseJSON(JSON.stringify(data));
			 var res=datastring[0].result;
			 var res1 = $.trim(res);
			 if(res1=="loginfirst")
			 {
				 search1();
			 }
			 
			 
			 else if(res1=="NOtasksavailable"){
				 var welcomename=datastring[0].assigned;
			$('#welcome').append('Welcome ' + welcomename);
				 			 console.log(datastring);
				var student = ' ';
				student += '<h2>';
				student += 'No tasks assigned to you at the moment';
				student += '</h2>';
				$('#tasktable').append(student);
		     }
			 else
			 {
				 var welcomename=datastring[0].assigned;
			$('#welcome').append('Welcome ' + welcomename);
			 console.log(datastring);
			 var student = ' ';
			 student += '<table class="view">';
			 student += '<tr><th>Task id</th> <th>Task Name</th> <th>Task Description</th>  <th>Task Createdtime</th> <th>Task Createdby</th> <th>Task Status</th> <th> change the status <br/>if completed</th></tr>';
			 var i=1;
			 $(datastring).each(function (index, item) {
			   var taskid = item.taskid;
               var taskname = item.taskname;
               var taskdescription = item.taskdescription;
               var taskstatus = item.taskstatus;
               var taskcreatedtime = item.taskcreatedtime;
			   var taskcreatedby= item.taskcreatedby;
			   student += '<tr id="rowremove">';
			   student += '<td class="tid">' + 
                                taskid + '</td>';
			   student += '<td>' + 
                                taskname + '</td>';
				
				student += '<td>' + 
                                taskdescription + '</td>';
				
				
				student += '<td>' + 
                                taskcreatedtime + '</td>';
				student += '<td>' + 
                                taskcreatedby + '</td>';
				student += '<td>' + 
                                taskstatus + '</td>';
				
				student += '<td>' + '<select id="'+taskid+'"> <option value=""></option>   <option value="nottaken">Not taken</option>    <option value="completed">Completed</option></select>' +'</td>';
				student += '</tr>';
			   i=i+1;
           });
		   student+='</table>';
		   $('#tasktable').append(student);
		  }
		  },
		  error:function(error){
			  alert(error);
		  }
      });
	  function search1(){
			self.transitionToRoute('login');
			 }
		},
		logout : function(){
			 $("#whole").css("margin-top", "0px");
			 var self=this;
			 Ember.$.ajax({
				 url:'/logout',
				 type:'GET',
				 success:function(data){
					 search();
				 },
				 error: function(error){
					 alert("error");
				 }
			 });
			 function search(){
			self.transitionToRoute('login');
			 }
		},
    viewtask: function() {
		var selmethod=$('#selectmethod').val();
		var self=this;
     Ember.$.ajax({
          url:'/viewtask',
          type:'GET',
		  data:{'selmethod':selmethod},
		  dataType:'json',
		  success:function(data){
			  $('#tasktable').empty();
			  $('#welcome').empty();
			 var datastring=$.parseJSON(JSON.stringify(data));
			 var res=datastring[0].result;
			 var res1 = $.trim(res);
			 if(res1=="loginfirst")
			 {
				 search1();
			 }
			 
			 
			 else if(res1=="NOtasksavailable"){
				 var welcomename=datastring[0].assigned;
			$('#welcome').append('Welcome ' + welcomename);
				 			 console.log(datastring);
				var student = ' ';
				student += '<h2>';
				student += 'No tasks assigned to you at the moment';
				student += '</h2>';
				$('#tasktable').append(student);
		     }
			 else
			 {
				 var welcomename=datastring[0].assigned;
			$('#welcome').append('Welcome ' + welcomename);
			 console.log(datastring);
			 var student = ' ';
			 student += '<table class="view">';
			 student += '<tr><th>Task id</th> <th>Task Name</th> <th>Task Description</th>  <th>Task Createdtime</th> <th>Task Createdby</th> <th>Task Status</th> <th> change the status <br/>if completed</th></tr>';
			 var i=1;
			 $(datastring).each(function (index, item) {
			   var taskid = item.taskid;
               var taskname = item.taskname;
               var taskdescription = item.taskdescription;
               var taskstatus = item.taskstatus;
               var taskcreatedtime = item.taskcreatedtime;
			   var taskcreatedby= item.taskcreatedby;
			   student += '<tr id="rowremove">';
			   student += '<td class="tid">' + 
                                taskid + '</td>';
			   student += '<td>' + 
                                taskname + '</td>';
				
				student += '<td>' + 
                                taskdescription + '</td>';
				
				
				student += '<td>' + 
                                taskcreatedtime + '</td>';
				student += '<td>' + 
                                taskcreatedby + '</td>';
				student += '<td>' + 
                                taskstatus + '</td>';
				
				student += '<td>' + '<select id="'+taskid+'"> <option value=""></option>   <option value="nottaken">Not taken</option>    <option value="completed">Completed</option></select>' +'</td>';
				student += '</tr>';
			   i=i+1;
           });
		   student+='</table>';
		   $('#tasktable').append(student);
		  }
		  },
		  error:function(error){
			  alert(error);
		  }
      });
	  function search1(){
			self.transitionToRoute('login');
			 }
    } ,
	selection:function(){
		var s="false";
		var self=this;
		   $('.view').find("td.tid").each(function(index){ 
			var myData = $(this).html();
			var tresult=$('#'+myData).val();
			let taskjson = {
				'tid' : myData,
				'tstatus' : tresult
           };
		    if(tresult=="nottaken" || tresult=="completed")
			{
				Ember.$.ajax({
				url:'/updatetask',
				type:'POST',
				data:taskjson,
				success:function(data){
					var selmethod=$('#selectmethod').val();
		var self=this;
     Ember.$.ajax({
          url:'/viewtask',
          type:'GET',
		  data:{'selmethod':selmethod},
		  dataType:'json',
		  success:function(data){
			  $('#tasktable').empty();
			  $('#welcome').empty();
			 var datastring=$.parseJSON(JSON.stringify(data));
			 var res=datastring[0].result;
			 var res1 = $.trim(res);
			 if(res1=="loginfirst")
			 {
				 search1();
			 }
			 
			 
			 else if(res1=="NOtasksavailable"){
				 var welcomename=datastring[0].assigned;
			$('#welcome').append('Welcome ' + welcomename);
				 			 console.log(datastring);
				var student = ' ';
				student += '<h2>';
				student += 'No tasks assigned to you at the moment';
				student += '</h2>';
				$('#tasktable').append(student);
		     }
			 else
			 {
				 var welcomename=datastring[0].assigned;
			$('#welcome').append('Welcome ' + welcomename);
			 console.log(datastring);
			 var student = ' ';
			 student += '<table class="view">';
			 student += '<tr><th>Task id</th> <th>Task Name</th> <th>Task Description</th>  <th>Task Createdtime</th> <th>Task Createdby</th> <th>Task Status</th> <th> change the status <br/>if completed</th></tr>';
			 var i=1;
			 $(datastring).each(function (index, item) {
			   var taskid = item.taskid;
               var taskname = item.taskname;
               var taskdescription = item.taskdescription;
               var taskstatus = item.taskstatus;
               var taskcreatedtime = item.taskcreatedtime;
			   var taskcreatedby= item.taskcreatedby;
			   student += '<tr id="rowremove">';
			   student += '<td class="tid">' + 
                                taskid + '</td>';
			   student += '<td>' + 
                                taskname + '</td>';
				
				student += '<td>' + 
                                taskdescription + '</td>';
				
				
				student += '<td>' + 
                                taskcreatedtime + '</td>';
				student += '<td>' + 
                                taskcreatedby + '</td>';
				student += '<td>' + 
                                taskstatus + '</td>';
				
				student += '<td>' + '<select id="'+taskid+'"> <option value=""></option>   <option value="nottaken">Not taken</option>    <option value="completed">Completed</option></select>' +'</td>';
				student += '</tr>';
			   i=i+1;
           });
		   student+='</table>';
		   $('#tasktable').append(student);
		  }
		  },
		  error:function(error){
			  alert(error);
		  }
      });
	  function search1(){
			self.transitionToRoute('login');
			 }
			},
		  error:function(error){
			  alert(error);
		  }
	});
	}
                });
			  $("#whole").css("margin-top", "-155px");
			self.transitionToRoute('tasklist');
	}
  }
});

