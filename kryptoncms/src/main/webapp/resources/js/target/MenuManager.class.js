define(["jquery","libraries/mootools-base","SocketHandler.class","./libraries/jquery.ui/sortable","./libraries/jquery.ui/draggable","./libraries/jquery.ui/droppable"],function(e,t,n){var r=new t.Class({initialize:function(){var t=this;t.subscribeSocket(),e(".nav,.dropdown-menu").sortable({placeholder:"ui-state-highlight",connectWith:"#bod",items:"li:not(.exclude)",stop:function(){var n=[];e.when(e(this).find("li a").each(function(){n.push(e(this).attr("data-menu-id"))})).then(function(){t.socketHandler.push(JSON.stringify({newOrder:n,admin:!0,parentId:null,action:"rearrange"}))})}}).droppable(),e(".nav,.dropdown-menu").disableSelection(),e("#bod").sortable({connectWith:"#admin_nav",activeClass:"ui-state-default",hoverClass:"ui-state-hover",accept:":not(.ui-sortable-helper)",items:"",receive:function(n,r){t.socketHandler.push(JSON.stringify({admin:!0,action:"delete",operandId:e(r.item).find("a").attr("data-menu-id")}))}}),e("#addMenu").click(function(){e("#genericModal").load(DOMAIN_CONFIGURATIONS.BASE_URL+"form/edu.asu.krypton.model.persist.db.Menu/")})},subscribeSocket:function(){var t=this;t.socketHandler=(new n({url:DOMAIN_CONFIGURATIONS.BASE_URL+"navigation/menu/echo"})).setOnMessageHandler(function(t){console.log(t);var t=JSON.parse(t.responseBody);t.action=="rearrange"?e.each(t.newOrder.reverse(),function(t,n){e("#"+n).parent("li").prependTo("#admin_nav ul")}):t.action=="delete"&&e("#"+t.operandId).parent("li").remove()}).subscribe()}.protect()});return r});