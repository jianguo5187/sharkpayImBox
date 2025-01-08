<template>
  <div>
    <el-upload :action="'#'" :http-request="onFileUpload" :accept="fileTypes==null?'':fileTypes.join(',')" :show-file-list="false"
      :disabled="disabled" :before-upload="beforeUpload"

               :on-remove="handleDelete"
               :on-preview="handlePictureCardPreview"
    >
      <slot></slot>
    </el-upload>


    <el-dialog
        :visible.sync="dialogVisible"
        title="预览"
        width="800"
        append-to-body
    >
      <img
          :src="dialogImageUrl"
          style="display: block; max-width: 100%; margin: 0 auto"
      />
    </el-dialog>
  </div>
</template>

<script>
	export default {
		name: "fileUpload",
		data() {
			return {
				loading: null,
        dialogImageUrl: "",
        dialogVisible: false,
				uploadHeaders: {
					"accessToken": sessionStorage.getItem('accessToken')
				}
			}
		},
		props: {
			action: {
				type: String,
				required: false
			},
			fileTypes: {
				type: Array,
				default: null
			},
			maxSize: {
				type: Number,
				default: null
			},
			showLoading: {
				type: Boolean,
				default: false
			},
			disabled: {
				type: Boolean,
				default: false
			}
		},
		methods: {
			onFileUpload(file) {
				// 展示加载条
				if (this.showLoading) {
					this.loading = this.$loading({
						lock: true,
						text: '正在上传...',
						spinner: 'el-icon-loading',
						background: 'rgba(0, 0, 0, 0.7)'
					});
				}

				let formData = new FormData()
				formData.append('file', file.file)
				this.$http({
					url: this.action,
					data: formData,
					method: 'post',
					headers: {
						'Content-Type': 'multipart/form-data'
					}
				}).then((data) => {
					this.$emit("success", data, file.file);
				}).catch((e) => {
					this.$emit("fail", e, file.file);
				}).finally(() => {
					this.loading && this.loading.close();
				})
			},
			beforeUpload(file) {
				// 校验文件类型
				if (this.fileTypes && this.fileTypes.length > 0) {
					let fileType = file.type;
					let t = this.fileTypes.find((t) => t.toLowerCase() === fileType);
					if (t === undefined) {
						this.$message.error(`文件格式错误，请上传以下格式的文件：${this.fileTypes.join("、")}`);
						return false;
					}
				}
				// 校验大小
				if (this.maxSize && file.size > this.maxSize) {
					this.$message.error(`文件大小不能超过 ${this.fileSizeStr}!`);
					return false;
				}

				this.$emit("before", file);
				return true;
			},
      // 删除图片
      handleDelete(file) {
        const findex = this.fileList.map(f => f.name).indexOf(file.name);
        if (findex > -1) {
          this.fileList.splice(findex, 1);
          this.$emit("input", this.listToString(this.fileList));
        }
      },
      // 预览
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },

      // 对象转成指定字符串分隔
      listToString(list, separator) {
        let strs = "";
        separator = separator || ",";
        for (let i in list) {
          if (list[i].url) {
            strs += list[i].url.replace(this.baseUrl, "") + separator;
          }
        }
        return strs != '' ? strs.substr(0, strs.length - 1) : '';
      },
		},
		computed: {
			fileSizeStr() {
				if (this.maxSize > 1024 * 1024) {
					return Math.round(this.maxSize / 1024 / 1024) + "M";
				}
				if (this.maxSize > 1024) {
					return Math.round(this.maxSize / 1024) + "KB";
				}
				return this.maxSize + "B";
			}
		}
	}
</script>

<style>
</style>