webpackJsonp([31],{LMdi:function(t,e){},dHnX:function(t,e,n){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=n("3idm"),i={name:"productAttrList",data:function(){return{list:null,total:null,listLoading:!0,listQuery:{pageNum:1,pageSize:5,type:this.$route.query.type},operateType:null,multipleSelection:[],operates:[{label:"删除",value:"deleteProductAttr"}]}},created:function(){this.getList()},methods:{getList:function(){var t=this;this.listLoading=!0,Object(a.c)(this.$route.query.cid,this.listQuery).then(function(e){t.listLoading=!1,t.list=e.data.list,t.total=e.data.total})},addProductAttr:function(){this.$router.push({path:"/pms/addProductAttr",query:{cid:this.$route.query.cid,type:this.$route.query.type}})},handleSelectionChange:function(t){this.multipleSelection=t},handleBatchOperate:function(){if(this.multipleSelection<1)this.$message({message:"请选择一条记录",type:"warning",duration:1e3});else if("deleteProductAttr"===this.operateType){for(var t=[],e=0;e<this.multipleSelection.length;e++)t.push(this.multipleSelection[e].id);this.handleDeleteProductAttr(t)}else this.$message({message:"请选择批量操作类型",type:"warning",duration:1e3})},handleSizeChange:function(t){this.listQuery.pageNum=1,this.listQuery.pageSize=t,this.getList()},handleCurrentChange:function(t){this.listQuery.pageNum=t,this.getList()},handleUpdate:function(t,e){this.$router.push({path:"/pms/updateProductAttr",query:{id:e.id}})},handleDeleteProductAttr:function(t){var e=this;this.$confirm("是否要删除该属性","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){var n=new URLSearchParams;n.append("ids",t),Object(a.b)(n).then(function(t){e.$message({message:"删除成功",type:"success",duration:1e3}),e.getList()})})},handleDelete:function(t,e){var n=[];n.push(e.id),this.handleDeleteProductAttr(n)}},filters:{inputTypeFilter:function(t){return 1===t?"从列表中选取":"手工录入"},selectTypeFilter:function(t){return 1===t?"单选":2===t?"多选":"唯一"}}},l={render:function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("el-card",{staticClass:"operate-container",attrs:{shadow:"never"}},[n("i",{staticClass:"el-icon-tickets",staticStyle:{"margin-top":"5px"}}),t._v(" "),n("span",{staticStyle:{"margin-top":"5px"}},[t._v("数据列表")]),t._v(" "),n("el-button",{staticClass:"btn-add",attrs:{size:"mini"},on:{click:function(e){t.addProductAttr()}}},[t._v("\n      添加\n    ")])],1),t._v(" "),n("div",{staticClass:"table-container"},[n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],ref:"productAttrTable",staticStyle:{width:"100%"},attrs:{data:t.list,border:""},on:{"selection-change":t.handleSelectionChange}},[n("el-table-column",{attrs:{type:"selection",width:"60",align:"center"}}),t._v(" "),n("el-table-column",{attrs:{label:"编号",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.id))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"属性名称",width:"140",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.name))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"商品类型",width:"140",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t.$route.query.cname))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"属性是否可选",width:"120",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t._f("selectTypeFilter")(e.row.selectType)))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"属性值的录入方式",width:"150",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(t._f("inputTypeFilter")(e.row.inputType)))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"可选值列表",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.inputList))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"排序",width:"100",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.sort))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"操作",width:"200",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-button",{attrs:{size:"mini"},on:{click:function(n){t.handleUpdate(e.$index,e.row)}}},[t._v("编辑\n          ")]),t._v(" "),n("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(n){t.handleDelete(e.$index,e.row)}}},[t._v("删除\n          ")])]}}])})],1)],1),t._v(" "),n("div",{staticClass:"batch-operate-container"},[n("el-select",{attrs:{size:"small",placeholder:"批量操作"},model:{value:t.operateType,callback:function(e){t.operateType=e},expression:"operateType"}},t._l(t.operates,function(t){return n("el-option",{key:t.value,attrs:{label:t.label,value:t.value}})})),t._v(" "),n("el-button",{staticClass:"search-button",staticStyle:{"margin-left":"20px"},attrs:{type:"primary",size:"small"},on:{click:function(e){t.handleBatchOperate()}}},[t._v("\n      确定\n    ")])],1),t._v(" "),n("div",{staticClass:"pagination-container"},[n("el-pagination",{attrs:{background:"",layout:"total, sizes,prev, pager, next,jumper","page-size":t.listQuery.pageSize,"page-sizes":[5,10,15],"current-page":t.listQuery.pageNum,total:t.total},on:{"size-change":t.handleSizeChange,"current-change":t.handleCurrentChange,"update:currentPage":function(e){t.$set(t.listQuery,"pageNum",e)}}})],1)],1)},staticRenderFns:[]};var r=n("VU/8")(i,l,!1,function(t){n("LMdi")},"data-v-ab54733e",null);e.default=r.exports}});
//# sourceMappingURL=31.75934a772bdf369ae515.js.map