webpackJsonp([30],{"+xQB":function(t,e){},eoyd:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var l=a("woOf"),i=a.n(l),o=a("vLgD");var n=a("UgCr"),s={pageNum:1,pageSize:5,flashPromotionId:null,flashPromotionSessionId:null},r={name:"flashPromotionProductRelationList",data:function(){return{listQuery:i()({},s),list:null,total:null,listLoading:!1,dialogVisible:!1,selectDialogVisible:!1,dialogData:{list:null,total:null,multipleSelection:[],listQuery:{keyword:null,pageNum:1,pageSize:5}},editDialogVisible:!1,flashProductRelation:{product:{}}}},created:function(){this.listQuery.flashPromotionId=this.$route.query.flashPromotionId,this.listQuery.flashPromotionSessionId=this.$route.query.flashPromotionSessionId,this.getList()},methods:{handleSizeChange:function(t){this.listQuery.pageNum=1,this.listQuery.pageSize=t,this.getList()},handleCurrentChange:function(t){this.listQuery.pageNum=t,this.getList()},handleSelectProduct:function(){this.selectDialogVisible=!0,this.getDialogList()},handleUpdate:function(t,e){this.editDialogVisible=!0,this.flashProductRelation=i()({},e)},handleDelete:function(t,e){var a=this;this.$confirm("是否要删除该商品?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){var t;(t=e.id,Object(o.a)({url:"/flashProductRelation/delete/"+t,method:"post"})).then(function(t){a.$message({type:"success",message:"删除成功!"}),a.getList()})})},handleSelectSearch:function(){this.getDialogList()},handleDialogSizeChange:function(t){this.dialogData.listQuery.pageNum=1,this.dialogData.listQuery.pageSize=t,this.getDialogList()},handleDialogCurrentChange:function(t){this.dialogData.listQuery.pageNum=t,this.getDialogList()},handleDialogSelectionChange:function(t){this.dialogData.multipleSelection=t},handleSelectDialogConfirm:function(){var t=this;if(this.dialogData.multipleSelection<1)this.$message({message:"请选择一条记录",type:"warning",duration:1e3});else{for(var e=[],a=0;a<this.dialogData.multipleSelection.length;a++)e.push({productId:this.dialogData.multipleSelection[a].id,flashPromotionId:this.listQuery.flashPromotionId,flashPromotionSessionId:this.listQuery.flashPromotionSessionId});this.$confirm("使用要进行添加操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){var a;(a=e,Object(o.a)({url:"/flashProductRelation/create",method:"post",data:a})).then(function(e){t.selectDialogVisible=!1,t.dialogData.multipleSelection=[],t.getList(),t.$message({type:"success",message:"添加成功!"})})})}},handleEditDialogConfirm:function(){var t=this;this.$confirm("是否要确认?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){var e,a;(e=t.flashProductRelation.id,a=t.flashProductRelation,Object(o.a)({url:"/flashProductRelation/update/"+e,method:"post",data:a})).then(function(e){t.$message({message:"修改成功！",type:"success"}),t.editDialogVisible=!1,t.getList()})})},getList:function(){var t,e=this;this.listLoading=!0,(t=this.listQuery,Object(o.a)({url:"/flashProductRelation/list",method:"get",params:t})).then(function(t){e.listLoading=!1,e.list=t.data.list,e.total=t.data.total})},getDialogList:function(){var t=this;Object(n.b)(this.dialogData.listQuery).then(function(e){t.dialogData.list=e.data.list,t.dialogData.total=e.data.total})}}},c={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("el-card",{staticClass:"operate-container",attrs:{shadow:"never"}},[a("i",{staticClass:"el-icon-tickets"}),t._v(" "),a("span",[t._v("数据列表")]),t._v(" "),a("el-button",{staticClass:"btn-add",staticStyle:{"margin-left":"20px"},attrs:{size:"mini"},on:{click:function(e){t.handleSelectProduct()}}},[t._v("添加")])],1),t._v(" "),a("div",{staticClass:"table-container"},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],ref:"productRelationTable",staticStyle:{width:"100%"},attrs:{data:t.list,border:""}},[a("el-table-column",{attrs:{label:"编号",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.id))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"商品名称",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.product.name))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"货号",width:"140",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("NO."+t._s(e.row.product.productSn))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"商品价格",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("￥"+t._s(e.row.product.price))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"剩余数量",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.product.stock))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"秒杀价格",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[null!==e.row.flashPromotionPrice?a("p",[t._v("\n            ￥"+t._s(e.row.flashPromotionPrice)+"\n          ")]):t._e()]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"秒杀数量",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.flashPromotionCount))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"限购数量",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.flashPromotionLimit))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"排序",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.sort))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"操作",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){t.handleUpdate(e.$index,e.row)}}},[t._v("编辑\n          ")]),t._v(" "),a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){t.handleDelete(e.$index,e.row)}}},[t._v("删除\n          ")])]}}])})],1)],1),t._v(" "),a("div",{staticClass:"pagination-container"},[a("el-pagination",{attrs:{background:"",layout:"total, sizes,prev, pager, next,jumper","current-page":t.listQuery.pageNum,"page-size":t.listQuery.pageSize,"page-sizes":[5,10,15],total:t.total},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange,"update:currentPage":function(e){t.$set(t.listQuery,"pageNum",e)}}})],1),t._v(" "),a("el-dialog",{attrs:{title:"选择商品",visible:t.selectDialogVisible,width:"50%"},on:{"update:visible":function(e){t.selectDialogVisible=e}}},[a("el-input",{staticStyle:{width:"250px","margin-bottom":"20px"},attrs:{size:"small",placeholder:"商品名称搜索"},model:{value:t.dialogData.listQuery.keyword,callback:function(e){t.$set(t.dialogData.listQuery,"keyword",e)},expression:"dialogData.listQuery.keyword"}},[a("el-button",{attrs:{slot:"append",icon:"el-icon-search"},on:{click:function(e){t.handleSelectSearch()}},slot:"append"})],1),t._v(" "),a("el-table",{attrs:{data:t.dialogData.list,border:""},on:{"selection-change":t.handleDialogSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"60",align:"center"}}),t._v(" "),a("el-table-column",{attrs:{label:"商品名称",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.name))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"货号",width:"160",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("NO."+t._s(e.row.productSn))]}}])}),t._v(" "),a("el-table-column",{attrs:{label:"价格",width:"120",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("￥"+t._s(e.row.price))]}}])})],1),t._v(" "),a("div",{staticClass:"pagination-container"},[a("el-pagination",{attrs:{background:"",layout:"prev, pager, next","current-page":t.dialogData.listQuery.pageNum,"page-size":t.dialogData.listQuery.pageSize,"page-sizes":[5,10,15],total:t.dialogData.total},on:{"size-change":t.handleDialogSizeChange,"current-change":t.handleDialogCurrentChange,"update:currentPage":function(e){t.$set(t.dialogData.listQuery,"pageNum",e)}}})],1),t._v(" "),a("div",{staticStyle:{clear:"both"}}),t._v(" "),a("div",{attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{size:"small"},on:{click:function(e){t.selectDialogVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{size:"small",type:"primary"},on:{click:function(e){t.handleSelectDialogConfirm()}}},[t._v("确 定")])],1)],1),t._v(" "),a("el-dialog",{attrs:{title:"编辑秒杀商品信息",visible:t.editDialogVisible,width:"40%"},on:{"update:visible":function(e){t.editDialogVisible=e}}},[a("el-form",{ref:"flashProductRelationForm",attrs:{model:t.flashProductRelation,"label-width":"150px",size:"small"}},[a("el-form-item",{attrs:{label:"商品名称："}},[a("span",[t._v(t._s(t.flashProductRelation.product.name))])]),t._v(" "),a("el-form-item",{attrs:{label:"货号："}},[a("span",[t._v("NO."+t._s(t.flashProductRelation.product.productSn))])]),t._v(" "),a("el-form-item",{attrs:{label:"商品价格："}},[a("span",[t._v("￥"+t._s(t.flashProductRelation.product.price))])]),t._v(" "),a("el-form-item",{attrs:{label:"秒杀价格："}},[a("el-input",{staticClass:"input-width",model:{value:t.flashProductRelation.flashPromotionPrice,callback:function(e){t.$set(t.flashProductRelation,"flashPromotionPrice",e)},expression:"flashProductRelation.flashPromotionPrice"}},[a("template",{slot:"prepend"},[t._v("￥")])],2)],1),t._v(" "),a("el-form-item",{attrs:{label:"剩余数量："}},[a("span",[t._v(t._s(t.flashProductRelation.product.stock))])]),t._v(" "),a("el-form-item",{attrs:{label:"秒杀数量："}},[a("el-input",{staticClass:"input-width",model:{value:t.flashProductRelation.flashPromotionCount,callback:function(e){t.$set(t.flashProductRelation,"flashPromotionCount",e)},expression:"flashProductRelation.flashPromotionCount"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"限购数量："}},[a("el-input",{staticClass:"input-width",model:{value:t.flashProductRelation.flashPromotionLimit,callback:function(e){t.$set(t.flashProductRelation,"flashPromotionLimit",e)},expression:"flashProductRelation.flashPromotionLimit"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"排序："}},[a("el-input",{staticClass:"input-width",model:{value:t.flashProductRelation.sort,callback:function(e){t.$set(t.flashProductRelation,"sort",e)},expression:"flashProductRelation.sort"}})],1)],1),t._v(" "),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{size:"small"},on:{click:function(e){t.editDialogVisible=!1}}},[t._v("取 消")]),t._v(" "),a("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(e){t.handleEditDialogConfirm()}}},[t._v("确 定")])],1)],1)],1)},staticRenderFns:[]};var u=a("VU/8")(r,c,!1,function(t){a("+xQB")},"data-v-b3262904",null);e.default=u.exports}});
//# sourceMappingURL=30.ef49da3e0dd38983cd45.js.map