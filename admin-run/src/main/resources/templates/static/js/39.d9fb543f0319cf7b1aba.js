webpackJsonp([39],{YEDJ:function(e,t,n){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l=n("FWz8"),r=["顺丰快递","圆通快递","中通快递","韵达快递"],a={name:"deliverOrderList",data:function(){return{list:[],companyOptions:r}},created:function(){this.list=this.$route.query.list},methods:{cancel:function(){this.$router.back()},confirm:function(){var e=this;this.$confirm("是否要进行发货操作?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){Object(l.c)(e.list).then(function(t){e.$router.back(),e.$message({type:"success",message:"发货成功!"})})}).catch(function(){e.$message({type:"info",message:"已取消发货"})})}}},o={render:function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("el-card",{staticClass:"operate-container",attrs:{shadow:"never"}},[n("i",{staticClass:"el-icon-tickets"}),e._v(" "),n("span",[e._v("发货列表")])]),e._v(" "),n("div",{staticClass:"table-container"},[n("el-table",{ref:"deliverOrderTable",staticStyle:{width:"100%"},attrs:{data:e.list,border:""}},[n("el-table-column",{attrs:{label:"订单编号",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.orderSn))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"收货人",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.receiverName))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"手机号码",width:"160",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.receiverPhone))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"邮政编码",width:"160",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.receiverPostCode))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"收货地址",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.address))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"配送方式",width:"160",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-select",{attrs:{placeholder:"请选择物流公司",size:"small"},model:{value:t.row.deliveryCompany,callback:function(n){e.$set(t.row,"deliveryCompany",n)},expression:"scope.row.deliveryCompany"}},e._l(e.companyOptions,function(e){return n("el-option",{key:e,attrs:{label:e,value:e}})}))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"物流单号",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-input",{attrs:{size:"small"},model:{value:t.row.deliverySn,callback:function(n){e.$set(t.row,"deliverySn",n)},expression:"scope.row.deliverySn"}})]}}])})],1),e._v(" "),n("div",{staticStyle:{"margin-top":"15px","text-align":"center"}},[n("el-button",{on:{click:e.cancel}},[e._v("取消")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:e.confirm}},[e._v("确定")])],1)],1)],1)},staticRenderFns:[]};var c=n("VU/8")(a,o,!1,function(e){n("cX2q")},null,null);t.default=c.exports},cX2q:function(e,t){}});
//# sourceMappingURL=39.d9fb543f0319cf7b1aba.js.map