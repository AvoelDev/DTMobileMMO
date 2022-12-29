package com.android.easy2pay;

/* loaded from: classes3.dex */
abstract class Resource {
    protected static final String STRING_CHECK_CHARGED_URL = "http://api.easy2pay.co/add-on/inquiry.php";
    protected static final String STRING_COPYRIGHT = "Copyright 2014 EASY2PAY SDK 1.1.0.5";
    protected static final String STRING_DESC_KEY = "description";
    protected static final String STRING_GET_PINCODE_URL = "http://api.easy2pay.co/add-on/init.php";
    protected static final String STRING_GET_PRICES_URL = "http://api.easy2pay.co/add-on/get-price-list2.php";
    protected static final String STRING_RELEASE_DATE = "2014-10-03";
    protected static final String STRING_TITLE_KEY = "title";
    protected static final String STRING_VERSION = "1.1.0.5";
    protected static final String TAG_NAME_DESC = "description";
    protected static final String TAG_NAME_PIN = "pin";
    protected static final String TAG_NAME_PRICE = "price";
    protected static final String TAG_NAME_PRICES = "prices";
    protected static final String TAG_NAME_PRICE_DESC = "priceDescription";
    protected static final String TAG_NAME_PRICE_ID = "priceId";
    protected static final String TAG_NAME_PTXID = "pTxId";
    protected static final String TAG_NAME_REFID = "referenceId";
    protected static final String TAG_NAME_SHORTCODE = "shortcode";
    protected static final String TAG_NAME_STATUS = "status";
    protected static final String TAG_NAME_STATUS_DETAIL = "statusDetail";
    protected static final String TAG_NAME_TITLE = "title";
    protected static final String TAG_NAME_TXID = "txId";
    protected static final String TAG_NAME_USERID = "userId";
    protected static final String STRING_APP_NAME = "Easy2Pay";
    protected static final String[] STRING_TITLE_VALUE = {STRING_APP_NAME, STRING_APP_NAME, STRING_APP_NAME, STRING_APP_NAME, STRING_APP_NAME};
    protected static final String[] STRING_DESC_VALUE = {"Do you want to buy this virtual goods?", "กรุณายืนยันการเรียกเก็บค่าบริการค่ะ", "Adakah anda ingin membeli ini barang-barang maya?", "Apakah Anda ingin membeli barang virtual ini?", "Bạn muốn mua hàng hóa ảo này?"};
    protected static final String[] STRING_WAITING_TITLE = {"Loading...", "กำลังโหลด กรุณารอสักครู่ค่ะ ...", "Memuatkan, sila tunggu ...", "Memuat, harap tunggu ...", "Đang tải, vui lòng đợi ..."};
    protected static final String[] STRING_PROGRESS_TITLE = {"The purchasing in progress, please wait...", "กำลังดำเนินการ กรุณารอซักครู่ค่ะ...", "Pembelian dalam proses, sila tunggu ...", "Pembelian berlangsung, harap tunggu ...", "Thu mua trong tiến trình, xin vui lòng chờ ..."};
    protected static final String[] STRING_ERROR_XML_IS_INVALID = {"System error has occurred: Cannot parse result XML tag from server!", "เกิดข้อผิดพลาดในระบบ: Can not parse result XML tag from server!", "Kesilapan sistem telah berlaku: Tidak dapat menghurai hasil tag XML dari pelayan!", "Kesalahan Sistem telah terjadi: Tidak dapat mengurai tag XML hasil dari server!", "Lỗi hệ thống đã xảy ra: không thể phân tích kết quả thẻ XML từ máy chủ!"};
    protected static final String[] STRING_ERROR_PRICE_IS_INVALID = {"Price is invalid!", "ขออภัย ราคาไม่ถูกต้องค่ะ", "Harga adalah tidak sah!", "Harga tidak valid!", "Giá là không hợp lệ!"};
    protected static final String[] STRING_ERROR_SIMCARD_INACTIVE = {"Error has occurred: SIM Card not inserted or using airplane mode!", "เกิดข้อผิดพลาด: Error has occurred: SIM Card not inserted or using airplane mode!", "Ralat telah berlaku: Kad SIM tidak dimasukkan atau menggunakan mod kapal terbang!", "Kesalahan telah terjadi: Kartu SIM tidak dimasukkan atau menggunakan mode pesawat!", "Lỗi đã xảy ra: thẻ SIM không thêm vào hoặc sử dụng chế độ máy bay!"};
    protected static final String[] STRING_EVENT_EASY2PAY_IS_CHARGING = {"Charging process is starting...", "กำลังเริ่มดำเนินการเรียกเก็บค่าบริการ...", "Proses pengecasan bermula ...", "Proses pengisian mulai ...", "Quá trình sạc bắt đầu ..."};
    protected static final String[] STRING_EVENT_USER_CANCEL_CHARGE = {"The purchasing has been canceled by user.", "ท่านได้ยกเลิกการเรียกเก็บค่าบริการ แล้วค่อยมาทำรายการใหม่นะคะ", "Pembelian telah dibatalkan oleh pengguna.", "Pembelian telah dibatalkan oleh pengguna.", "Việc mua bán đã bị hủy bỏ bởi người dùng."};
    protected static final String[] STRING_EVENT_BACKGROUND_CHARGING = {"The charging is running in background process.", "The charging is running in background process.", "Pengecasan sedang berjalan dalam proses latar belakang.", "Pengisian berjalan dalam proses latar belakang.", "Tính phí đang chạy trong quá trình nền."};
    protected static final String[] STRING_ALERT_CANNOT_SEND_SMS = {"SMS sending failed! Please check your SIM card, your network and try again.", "เกิดข้อผิดพลาด: ส่ง SMS ออกไม่ได้ค่ะ กรุณาลองตรวจสอบว่าท่านได้ใส่ SIM การ์ดแล้ว หรือ ตรวจสอบเครือข่ายว่าใช้งานได้ปกติ แล้วค่อยลองอีกครั้งนะคะ", "SMS menghantar gagal! Sila semak kad SIM anda, rangkaian anda dan cuba lagi.", "Pengiriman SMS gagal! Silakan periksa kartu SIM Anda, jaringan Anda dan coba lagi.", "Gửi tin nhắn SMS không thành công! Vui lòng kiểm tra thẻ SIM của bạn, mạng của bạn và thử lại."};
    protected static final String[] STRING_ALERT_CANNOT_CHARGING = {"Purchasing fail! Please try again.", "ขออภัยค่ะ การเรียกเก็บค่าบริการไม่สำเร็จ กรุณาลองใหม่อีกครั้งค่ะ", "Pembelian gagal! Sila cuba sekali lagi.", "Pembelian gagal! Silakan coba lagi.", "Mua không! Vui lòng thử lại."};
    protected static final String[] STRING_ALERT_BACKGROUND_CHARGING = {"Purchasing take a long time. You do not need to do it again. Purchasing notification will be shown once it has been done.", "การดำเนินการใช้เวลานานกว่าปกติ ท่านไม่จำเป็นต้องทำรายการซ้ำ หากการทำรายการสำเร็จแล้วจะทำการแจ้งให้ทราบในภายหลังค่ะ", "Pembelian mengambil masa yang lama. Anda tidak perlu untuk melakukannya lagi. Pemberitahuan Pembelian akan ditunjukkan apabila ia telah dilakukan.", "Pembelian memakan waktu yang lama. Anda tidak perlu melakukannya lagi. Pembelian pemberitahuan akan ditampilkan setelah telah dilakukan.", "Mua mất một thời gian dài. Bạn không cần phải làm điều đó một lần nữa. Thông báo mua hàng sẽ được hiển thị khi nó đã được thực hiện."};
    protected static final String[] STRING_WAITING_FOR_BACKGROUND_CHARGING = {"The purchasing is in progress. You do not need to do it again. Purchasing notification will be shown once it has been done.", "ท่านเพิ่งทำรายการไป และระบบตัดเงินใช้เวลาทำรายการนานกว่าปกติ ท่านไม่จำเป็นต้องทำรายการซ้ำ หากการทำรายการสำเร็จแล้วจะทำการแจ้งให้ทราบในภายหลังค่ะ", "Pembelian sedang dijalankan. Anda tidak perlu untuk melakukannya lagi. Pemberitahuan Pembelian akan ditunjukkan apabila ia telah dilakukan.", "Beli sedang berlangsung. Anda tidak perlu melakukannya lagi. Pembelian pemberitahuan akan ditampilkan setelah telah dilakukan.", "Việc mua bán được tiến hành. Bạn không cần phải làm điều đó một lần nữa. Thông báo mua hàng sẽ được hiển thị khi nó đã được thực hiện."};
    protected static final String[] STRING_ALERT_CHARGED = {"Thank you! Your purchase was successful.", "การตัดเงินเสร็จเรียบร้อยแล้ว ขอบคุณที่ใช้บริการค่ะ", "Terima kasih! Pembelian anda telah berjaya.", "Terima kasih! Pembelian Anda berhasil.", "Cảm ơn bạn! Mua hàng của bạn đã thành công."};
    protected static final String[] STRING_PG_DIALOG_BACK_PRESSED = {"Purchasing take a long time. You do not need to do it again. Purchasing notification will be shown once it has been done.", "การดำเนินการใช้เวลานานกว่าปกติ ท่านไม่จำเป็นต้องทำรายการซ้ำ หากการทำรายการสำเร็จแล้วจะทำการแจ้งให้ทราบในภายหลังค่ะ", "Pembelian mengambil masa yang lama. Anda tidak perlu untuk melakukannya lagi. Pemberitahuan Pembelian akan ditunjukkan apabila ia telah dilakukan.", "Pembelian memakan waktu yang lama. Anda tidak perlu melakukannya lagi. Pembelian pemberitahuan akan ditampilkan setelah telah dilakukan.", "Mua mất một thời gian dài. Bạn không cần phải làm điều đó một lần nữa. Thông báo mua hàng sẽ được hiển thị khi nó đã được thực hiện."};
    protected static final String[] TXT_BUTTON_OK = {"OK", "ตกลง", "OK", "Oke", "OK"};
    protected static final String[] TXT_BUTTON_CANCEL = {"Cancel", "ยกเลิก", "Batal", "Membatalkan", "Hủy bỏ"};
    protected static final String[] TXT_BUTTON_CLOSE = {"Close", "ปิด", "Tutup", "Menutup", "Đóng"};

    Resource() {
    }
}
