1. Thông tin đăng nhập app và firebase :

 - Hiện tại t tích hợp đăng nhập bằng tài khoản có trên server Firebase rồi, có 2 tài khoản là 
	+ team@gmail.com/123456 : tài khoản cho team
	+ owner@gmail.com/123456 : tài khoản cho chủ sân
 - 	

2. Tổng quan về project :
	- Thư viện, thư mục :
		+ một số thư viện cho material design
		+ 1 custom view : roundedimageView - ảnh bo tròn
		+ các thư mục chính : trừ MainActivity ra, các Acitivy ở trong mục UI, Fragments ở trong thư mục fragment,
	các custom Dialog ở thư mục dialog, các model ở trong thư mục model
		+ framelayout để hiển thị các fragment có id là R.id.container	
	- Các model chính :
		+ User : model người dùng : có ID và UserType ( 1. OWNER ( chủ sân)  2.TEAM ( đội bóng ) - đáng chú ý)
		+ Pitch : model sân bóng
		+ Match : model trận đấu
	- Cấu trúc dữ liệu của project : 
		+ tạm thời với chức năng đăng ký , đăng nhập : vì firebase cung cấp chức năng Authen sẵn,khi tạo 1 user thì
firebase tự lưu rineg 1 chỗ với 4 thông tin : email,pass,image,name, id nhưng mình cần theem1  trường mà nó không hỗ trợ 
custom lại nên đành lấy ra id, password, name,image chuyển vào lưu ở realtime db firebase
				gốc db -> users. nhánh users này chưa các child là các user, mỗi user là một child với 
	key của child là id của users. Mỗi users được lưu : name, phone, type, imageURL. 
		+ sau này
	
 		