<template>
	<el-drawer title="快捷回复" size="700px"  :visible.sync="visible" direction="rtl" :before-close="onClose">
		<div class="chat-quick-answer" v-loading="loading" 
		 element-loading-text="拼命加载中">
      <el-scrollbar  class="chat-quick-answer-scrollbar" ref="scrollbar" id="historyScrollbar" >
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
            >新增</el-button>
          </el-col>
        </el-row>

        <el-table
            :data="tableData"
            ref="table"
            style="width: 100%;"
            border
        >

          <el-table-column label="快捷" align="center" width="100">
            <template slot-scope="scope">
              <el-button
                  size="medium"
                  type="primary"
                  @click="handleUser(scope.row)"
                  v-show="scope.row.id > 0"
              >使用</el-button>
            </template>
          </el-table-column>
          <el-table-column prop="content" label="回复信息">
            <template slot-scope="scope">
              <el-input type="textarea" v-model="scope.row.content" clearable v-show="scope.row.id == cellIndex" />
              <p v-show="scope.row.id != cellIndex" v-html="$emo.transform(scope.row.content)"></p>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleSave(scope.row)"
                  v-show="scope.row.id == cellIndex"
              >保存</el-button>
              <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-show="scope.row.id != cellIndex"
              >修改</el-button>
              <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-show="scope.row.id != 0"
              >删除</el-button>
              <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-close"
                  @click="handleCancle(scope.row)"
                  v-show="scope.row.id == cellIndex"
              >取消</el-button>
            </template>
          </el-table-column>
        </el-table>

<!--			<el-scrollbar  class="chat-quick-answer-scrollbar" ref="scrollbar" id="historyScrollbar" >-->
<!--				<ul>-->
<!--					<li v-for="(msgInfo,idx) in messages" :key="idx">-->
<!--						<chat-message-item :mode="2" :mine="msgInfo.sendId == mine.id" :headImage="headImage(msgInfo)" :showName="showName(msgInfo)"-->
<!--						 :msgInfo="msgInfo" :menu="false">-->
<!--						</chat-message-item>-->
<!--					</li>-->
<!--				</ul>-->
			</el-scrollbar>
		</div>
	</el-drawer>
</template>

<script>
	import ChatMessageItem from './ChatMessageItem.vue';
  import FileUpload from "../common/FileUpload.vue";

	export default {
		name: 'chatQuickAnswer',
		components: {
      FileUpload,
			ChatMessageItem
		},
		props: {
			visible: {
				type: Boolean
			},
			// chat: {
			// 	type: Object
			// },
			// friend: {
			// 	type: Object
			// },
			// group: {
			// 	type: Object
			// },
			// groupMembers: {
			// 	type: Array,
			// }
		},
		data() {
			return {
				loadAll: false,
				loading: false,

        cellIndex:undefined,
        tableData: [
        ],
			}
		},
		methods: {
			onClose() {
				this.loadAll = false;
				this.$emit('close');
			},
      onFindQuickAnswerMessage(){
        this.$http({
          url: `/quickAnswerMessage/loadAllQuickAnswerMessage`,
          method: "get"
        }).then((quickAnswerMessages) => {
          console.log(quickAnswerMessages);
          this.tableData = quickAnswerMessages;
        })
      },
      handleUser(row){
        this.loadAll = false;
        this.cellIndex = 0;
        this.tableData = [];
        this.$emit("quickanswer", row.content);
      },
      handleAdd(){
        console.log('handleAdd');
        // this.tableData.push({id:0});
        this.tableData.push({id:0,content:''});
        this.cellIndex = 0;
        this.$nextTick(() => {
          // 滚动到新增行的位置
          const table = this.$refs.table;
          if (table) {
            table.$el.querySelector('.el-table__body-wrapper').scrollTop = table.$el.querySelector('.el-table__body-wrapper').scrollHeight - 140;
          }
        });
      },
      handleSave(row){
        console.log(row);

        this.$http({
          url: `/quickAnswerMessage/saveQuickAnswerMessage`,
          method: "post",
          data: row
        }).then(() => {
          if(row.id == 0){
            this.$message.success("添加成功");
          }else{
            this.$message.success("修改成功");
          }
          this.cellIndex = 0;
          this.tableData = [];
          this.onFindQuickAnswerMessage();
        })
      },
      handleUpdate(row) {
        this.cellIndex = row.id;
      },
      handleDelete(row) {
        this.$confirm(`确定要删除这行数据吗？`, '确认解除?', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: `/quickAnswerMessage/deleteQuickAnswerMessage/${row.id}`,
            method: 'delete'
          }).then((data) => {
            this.$message.success("删除成功");
            this.cellIndex = 0;
            this.tableData = [];
            this.onFindQuickAnswerMessage();
          })
        })
      },
      handleCancle(row){
        this.cellIndex = 0;
        this.onFindQuickAnswerMessage();
      },

			// onScroll() {
			// 	let high = this.$refs.scrollbar.$refs.wrap.scrollTop; //距离顶部的距离
			// 	let timeDiff = new Date().getTime() - this.lastScrollTime.getTime();
			// 	if ( high < 30 && timeDiff>500) {
			// 		this.lastScrollTime = new Date();
			// 		this.loadMessages();
			//
			// 	}
			// },
			// loadMessages() {
			// 	if(this.loadAll){
			// 		return this.$message.success("已到达顶部");
			// 	}
			// 	let param = {
			// 		page: this.page++,
			// 		size: this.size
			// 	}
			// 	if (this.chat.type == 'GROUP') {
			// 		param.groupId = this.group.id;
			// 	} else {
			// 		param.friendId = this.friend.id;
			// 	}
			// 	this.loading = true;
			// 	this.$http({
			// 		url: this.histroyAction,
			// 		method: 'get',
			// 		params: param
			// 	}).then(messages => {
			// 		messages.forEach(m => this.messages.unshift(m));
			// 		this.loading = false;
			// 		if(messages.length <this.size){
			// 			this.loadAll = true;
			// 		}
			// 		this.refreshScrollPos();
			// 	}).catch(()=>{
			// 		this.loading = false;
			// 	})
			// },
			// showName(msgInfo) {
			// 	if (this.chat.type == 'GROUP') {
			// 		let member = this.groupMembers.find((m) => m.userId == msgInfo.sendId);
			// 		return member ? member.aliasName : "";
			// 	} else {
			// 		return msgInfo.sendId == this.mine.id ? this.mine.nickName : this.chat.showName
			// 	}
			// },
			// headImage(msgInfo) {
			// 	if (this.chat.type == 'GROUP') {
			// 		let member = this.groupMembers.find((m) => m.userId == msgInfo.sendId);
			// 		return member ? member.headImage : "";
			// 	} else {
			// 		return msgInfo.sendId == this.mine.id ? this.mine.headImageThumb : this.chat.headImage
			// 	}
			// },
			// refreshScrollPos(){
			// 	let scrollWrap =  this.$refs.scrollbar.$refs.wrap;
			// 	let scrollHeight = scrollWrap.scrollHeight;
			// 	let scrollTop = scrollWrap.scrollTop;
			// 	this.$nextTick(() => {
			// 		let offsetTop = scrollWrap.scrollHeight - scrollHeight;
			// 		scrollWrap.scrollTop = scrollTop + offsetTop;
			// 		// 滚动条没出来，继续加载
			// 		if(scrollWrap.scrollHeight == scrollHeight){
			// 			this.loadMessages();
			// 		}
			// 	});
			// }
		},
		watch: {
			visible: {
				handler(newValue, oldValue) {
					if (newValue) {
						// this.loadMessages();
            this.cellIndex = 0;
            this.tableData = [];
            this.onFindQuickAnswerMessage();
						// this.$nextTick(() => {
						// 	document.getElementById('historyScrollbar').addEventListener("mousewheel", this.onScroll,true);
						// });
					}
				}
			}
		}
	}
</script>

<style lang="scss">
	.chat-quick-answer {
		display: flex;
		height: 100%;
		
		.chat-quick-answer-scrollbar {
			flex: 1;
			.el-scrollbar__thumb {
			    background-color: #555555;
			}
			ul {
				padding: 20px;

				li {
					list-style-type: none;
				}
			}
		}
	}
</style>
