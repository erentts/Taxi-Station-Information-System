-- PERSONEL TABLOSU
create table personel(
	ssn char(11) not null primary key,
	ad varchar(20) not null,
	soyad varchar(20) not null,
	dogum_tarihi date not null,
	maas int not null,
	kan_grubu varchar(4) not null,
	arac_plaka varchar(7) not null,
	superssn char(11),
	constraint fk_ssn foreign key(superssn) references  personel(ssn)  on delete cascade
);

-- ARAÇLAR TABLOSU
create table araclar(
	plaka_no varchar(7) not null primary key,
	marka varchar(20) not null,
	model varchar(20) not null,
	kayit_tarihi date not null,
	sofor_id char(11) not null,
	constraint fk_sofor foreign key(sofor_id) references  personel(ssn)  on delete cascade
);


-- MUSTERILER TABLOSU
create table musteriler(
	tc char(11) not null primary key,
	ad varchar(20) not null,
	soyad varchar(20) not null,
	adres varchar(50) not null,
	cinsiyet varchar(5) not null,
	toplam_yolculuk_sayisi integer not null
);

-- YOLCULUK TABLOSU
create table yolculuk(
	yolculuk_id int not null primary key,
	baslangic varchar(20) not null,
	bitis varchar(20) not null,
	tutar int not null,
	arac_plaka varchar(7) not null,
	musteri_id char(11) not null,
	sofor_id char(11) not null,
	constraint fk_plaka foreign key(arac_plaka) references  araclar(plaka_no)  on delete cascade,
	constraint fk_musteri foreign key(musteri_id) references  musteriler(tc)  on delete cascade,
	constraint fk_sofor foreign key(sofor_id) references  personel(ssn)  on delete cascade
)