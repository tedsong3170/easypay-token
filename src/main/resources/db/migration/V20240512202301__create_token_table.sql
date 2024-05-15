-- 테이블 생성 SQL - payment_method_info
CREATE TABLE payment_method_info
(
  payment_method_id    char(32)       NOT NULL,
  customer_di          char(64)       NOT NULL,
  mid                  char(32)       NOT NULL,
  method               varchar(50)    NOT NULL,
  card_info            json           NULL,
  create_at            timestamp      NOT NULL,
  deleted_at           timestamp      NULL,
  nick_name            varchar(50)    NOT NULL,
  PRIMARY KEY (payment_method_id)
);

create index idx_payment_method_info_di on payment_method_info(customer_di);
create index idx_payment_method_info_mid on payment_method_info(mid);

-- 테이블 Comment 설정 SQL - payment_method_info
COMMENT ON TABLE payment_method_info IS '결제수단정보';

-- 컬럼 Comment 설정 SQL - payment_method_info.payment_method_id
COMMENT ON COLUMN payment_method_info.payment_method_id IS '결제수단ID';

-- 컬럼 Comment 설정 SQL - payment_method_info.customer_di
COMMENT ON COLUMN payment_method_info.customer_di IS '사용자DI';

-- 컬럼 Comment 설정 SQL - payment_method_info.mid
COMMENT ON COLUMN payment_method_info.mid IS '가맹점ID';

-- 컬럼 Comment 설정 SQL - payment_method_info.method
COMMENT ON COLUMN payment_method_info.method IS '결제수단. card, account 등';

-- 컬럼 Comment 설정 SQL - payment_method_info.card_info
COMMENT ON COLUMN payment_method_info.card_info IS '카드정보';

-- 컬럼 Comment 설정 SQL - payment_method_info.create_at
COMMENT ON COLUMN payment_method_info.create_at IS '등록일';

-- 컬럼 Comment 설정 SQL - payment_method_info.deleted_at
COMMENT ON COLUMN payment_method_info.deleted_at IS '삭제일';

-- 컬럼 Comment 설정 SQL - payment_method_info.nick_name
COMMENT ON COLUMN payment_method_info.nick_name IS '별칭';



-- 테이블 생성 SQL - payment_token_info
CREATE TABLE payment_token_info
(
  token                varchar(300)          NOT NULL,
  payment_method_id    char(32)          NOT NULL,
  payment_id            char(32)          NOT NULL,
  expect_amount        numeric(18, 0)    NOT NULL,
  issue_at             timestamp         NOT NULL,
  verify_at            timestamp         NULL,
  PRIMARY KEY (token),
  constraint uk_payment_token_info_paymentId unique(payment_id)
);

-- 테이블 Comment 설정 SQL - payment_token_info
COMMENT ON TABLE payment_token_info IS '결제토큰정보';

-- 컬럼 Comment 설정 SQL - payment_token_info.token
COMMENT ON COLUMN payment_token_info.token IS '결제토큰';

-- 컬럼 Comment 설정 SQL - payment_token_info.payment_method_id
COMMENT ON COLUMN payment_token_info.payment_method_id IS '결제수단ID';

-- 컬럼 Comment 설정 SQL - payment_token_info.payment_id
COMMENT ON COLUMN payment_token_info.payment_id IS '결제ID';

-- 컬럼 Comment 설정 SQL - payment_token_info.expect_amount
COMMENT ON COLUMN payment_token_info.expect_amount IS '결제예상금액';

-- 컬럼 Comment 설정 SQL - payment_token_info.issue_at
COMMENT ON COLUMN payment_token_info.issue_at IS '발급일시';

-- 컬럼 Comment 설정 SQL - payment_token_info.verify_at
COMMENT ON COLUMN payment_token_info.verify_at IS '유효성확인일시';


-- 테이블 생성 SQL - card_bin_info
CREATE TABLE card_bin_info
(
  bin          varchar(50)    NOT NULL,
  company      varchar(50)    NOT NULL,
  owner_type    varchar(50)    NOT NULL,
  brand        varchar(50)    NOT NULL,
  approval_url  varchar(300)   NOT NULL,
  PRIMARY KEY (bin)
);

-- 테이블 Comment 설정 SQL - card_bin_info
COMMENT ON TABLE card_bin_info IS '신용카드BIN정보';

-- 컬럼 Comment 설정 SQL - card_bin_info.bin
COMMENT ON COLUMN card_bin_info.bin IS 'BIN번호';

-- 컬럼 Comment 설정 SQL - card_bin_info.company
COMMENT ON COLUMN card_bin_info.company IS '발급사';

-- 컬럼 Comment 설정 SQL - card_bin_info.owner_type
COMMENT ON COLUMN card_bin_info.owner_type IS '카드구분';

-- 컬럼 Comment 설정 SQL - card_bin_info.brand
COMMENT ON COLUMN card_bin_info.brand IS '브랜드';

-- 컬럼 Comment 설정 SQL - card_bin_info.approval_url
COMMENT ON COLUMN card_bin_info.approval_url IS '승인URL';
