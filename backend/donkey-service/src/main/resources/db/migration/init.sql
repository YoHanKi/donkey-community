-- 회원 테이블
DROP TABLE  if exists member;
CREATE TABLE IF NOT EXISTS member (
    member_id	            VARCHAR(36)	PRIMARY KEY,
    authority_id	        VARCHAR(36),
    name	                VARCHAR(20) NOT NULL,
    nickname	            VARCHAR(20) NOT NULL UNIQUE, --유니크 추가
    email	                VARCHAR(100) NOT NULL UNIQUE,
    password	            VARCHAR(255) NOT NULL,
    gender	                VARCHAR(1),
    phone	                VARCHAR(20) NOT NULL,
    password_question_id	VARCHAR(36) NOT NULL,
    find_password_answer	VARCHAR(100) NOT NULL, -- 크기 수정
    withdrawal              BOOLEAN NOT NULL DEFAULT FALSE,
    create_date             TIMESTAMP DEFAULT NOW(),
    mod_date                TIMESTAMP DEFAULT NOW()
);

-- 권한 테이블
DROP TABLE  if exists member_role;
CREATE TABLE IF NOT EXISTS member_role ( -- 이름 변경 authorities -> member_role
    authority_id	        VARCHAR(36)	PRIMARY KEY,
    authority_name	        VARCHAR(100) NOT NULL
);

-- 패스워드 질문 테이블
DROP TABLE  if exists password_question;
CREATE TABLE IF NOT EXISTS password_question (
    password_question_id	VARCHAR(36)	PRIMARY KEY,
    question	            VARCHAR(200) NOT NULL
);


-- 회원 관심 업종 테이블
DROP TABLE  if exists member_interests;
CREATE TABLE IF NOT EXISTS member_interests (
    interests_id	        VARCHAR(36)	PRIMARY KEY,
    industry_id	            VARCHAR(36) NOT NULL,
    member_id	            VARCHAR(36) NOT NULL
);

-- 회원 인증 기업 테이블 (보류)
-- CREATE TABLE certification (
--     com_certification_id	VARCHAR(36)	PRIMARY KEY,
--     member_id	VARCHAR(36) ,
--     com_id	VARCHAR(36) ,
--     com_certification	boolean	,
--     authority_id	VARCHAR(36)
-- );

-- 기업 테이블
DROP TABLE  if exists companies;
CREATE TABLE IF NOT EXISTS companies (
    com_id	                VARCHAR(36)	PRIMARY KEY,
    reg_num	                VARCHAR(14) UNIQUE NOT NULL,
    com_name	            VARCHAR(20) NOT NULL
);

-- 업종 테이블(산업 테이블)
DROP TABLE  if exists industry;
CREATE TABLE IF NOT EXISTS industry (
    industry_id	            VARCHAR(36)	PRIMARY KEY,
    industry_name	        VARCHAR(100) NOT NULL UNIQUE , --유니크 추가
    industry_description	VARCHAR(255)
);

-- 리뷰 테이블
DROP TABLE  if exists reviews;
CREATE TABLE IF NOT EXISTS reviews (
    review_id	            VARCHAR(36)	PRIMARY KEY,
    com_id	                VARCHAR(36) NOT NULL,
    member_id	            VARCHAR(36) NOT NULL,
    review_title	        VARCHAR(100) NOT NULL,
    review_content	        VARCHAR(200) NOT NULL,
    create_date	            TIMESTAMP   DEFAULT NOW(),
    mod_date	            TIMESTAMP   DEFAULT NOW(),
    mod_check	            boolean NOT NULL DEFAULT FALSE -- NOT NULL 추가 해야 됨
);

-- 리뷰 항목별 점수 테이블
DROP TABLE  if exists review_score;
CREATE TABLE IF NOT EXISTS review_score (
    score_id	            VARCHAR(36)	PRIMARY KEY,
    review_id	            VARCHAR(36) NOT NULL,
    review_Categories_id	VARCHAR(36) NOT NULL,
    score	                INT NOT NULL CHECK (score > 0 AND score <= 5)
);

-- 리뷰 항목 테이블
DROP TABLE  if exists Review_Categories;
CREATE TABLE IF NOT EXISTS Review_Categories (
    review_Categories_id	VARCHAR(36)	PRIMARY KEY,
    category_name	        VARCHAR(100) NOT NULL UNIQUE -- UNIQUE 추가
    );

-- 게시판 테이블
DROP TABLE  if exists board;
CREATE TABLE IF NOT EXISTS board (
    board_id	            VARCHAR(36)	PRIMARY KEY, --"commu_" 삭제
    board_name              VARCHAR(20),
    industry_id	            VARCHAR(36) ,
    com_id	                VARCHAR(36) ,
    board_create_date	    TIMESTAMP DEFAULT NOW(), --"commu_" 삭제
    board_creator	        VARCHAR(36) NOT NULL, -- 운영진, "commu_" 삭제, 이름변경
    commu_board_request_approve BOOLEAN,
    board_requester	        VARCHAR(36) NOT NULL -- 생성요청자 회원ID, 이름변경
);

-- 게시글 테이블
DROP TABLE  if exists Document;
CREATE TABLE IF NOT EXISTS Document (
    doc_id	                VARCHAR(36)	PRIMARY KEY,
    board_id	            VARCHAR(36) NOT NULL, --"commu_" 삭제
    industry_id	            VARCHAR(36) NOT NULL,
    doc_title	            VARCHAR(100) NOT NULL, -- name -> title 변경
    doc_content	            TEXT NOT NULL,
    doc_create_date	        TIMESTAMP	DEFAULT NOW(),
    doc_creator	            VARCHAR(36)	NOT NULL, --작성자
    doc_mod_date	        TIMESTAMP	DEFAULT NOW(),
    doc_modifier	        VARCHAR(36)	,
    doc_visible	            BOOLEAN	NOT NULL DEFAULT true -- NOT NULL 추가 해야 됨
);

-- 댓글 테이블
DROP TABLE  if exists comment;
CREATE TABLE IF NOT EXISTS comment (
    comment_id	            VARCHAR(36)	PRIMARY KEY,
    doc_id	                VARCHAR(36) NOT NULL,
    comment_password	    VARCHAR(30)	,
    comment_creator_ip	    VARCHAR(16)	,
    comment_content	        VARCHAR(200) NOT NULL,
    comment_create_date	    TIMESTAMP	DEFAULT NOW(),
    comment_creator	        VARCHAR(36)	,
    comment_mod_date	    TIMESTAMP	DEFAULT NOW(),
    comment_modifier	    VARCHAR(36)	,
    comment_visible	        BOOLEAN	NOT NULL DEFAULT true, -- NOT NULL 추가 해야 됨
    anony_nickname          VARCHAR(20)
);

-- 좋아요 테이블
DROP TABLE  if exists like_it;
CREATE TABLE IF NOT EXISTS like_it (
    like_id	                VARCHAR(36)	PRIMARY KEY,
    like_count	            BIGINT  DEFAULT 0
);

-- 조회수 테이블
DROP TABLE  if exists viewership;
CREATE TABLE IF NOT EXISTS viewership (
    view_id	                VARCHAR(36)	PRIMARY KEY,
    view_count	            BIGINT DEFAULT 0
);

-- 신고 테이블
DROP TABLE  if exists report;
CREATE TABLE IF NOT EXISTS report (
    report_id	            VARCHAR(36)	PRIMARY KEY,
    reporter	            VARCHAR(20)	, -- NOT NULL 제거, 이름 변경
    reporter_ip	            VARCHAR(36) , -- NOT NULL 제거
    report_date	            TIMESTAMP DEFAULT NOW(),
    report_judge	        BOOLEAN	NOT NULL DEFAULT FALSE, -- NOT NULL 추가 해야 됨
    report_type             BIGINT NOT NULL,
    report_thing            VARCHAR(36) NOT NULL,
    report_content	        VARCHAR(200) NOT NULL
);

-- 좋아요 저장 리스트
DROP TABLE  if exists like_list;
CREATE TABLE IF NOT EXISTS like_list (
    Like_id             VARCHAR(36) PRIMARY KEY,
    user_ip             VARCHAR(36) NOT NULL,
    doc_com_name        VARCHAR(36) NOT NULL,
    create_date	        TIMESTAMP	DEFAULT NOW()
);

-- Member Role 데이터 삽입
INSERT INTO member_role (authority_id, authority_name) VALUES
                                                           ('3f27692d-5d32-4355-a859-d31dda20fd7d', 'ADMIN'),
                                                           ('a2e92f88-ea8b-4b50-8a07-4b64aa331e2a', 'USER');


-- Password Question 데이터 삽입
INSERT INTO password_question (password_question_id, question) VALUES
                                                                   ('55469a0e-8523-4f13-98bc-3bc7af528138', '좋아하는 동물은?'),
                                                                   ('a806a8c8-cbdf-4b77-8fdb-46dab77efbfe', '좋아하는 색깔은?'),
                                                                   ('5b05b2f8-ff0c-4d18-8b8a-22f07b0304f3', '태어난 도시는?'),
                                                                   ('d04c6b11-4144-46be-9a5e-da0e40065930', '첫 학교 이름은?'),
                                                                   ('2d93222b-6ca7-43a1-b9b9-bcdb1f09820b', '가장 좋아하는 책은?'),
                                                                   ('3c65f854-bbfe-4c1c-afc4-e0635ce15e08', '첫 직장이 어느 도시에 있었나요?'),
                                                                   ('a570421e-002a-4e0c-ace2-537f7a65ef90', '어린 시절 별명은?'),
                                                                   ('b86f45bb-0652-49fe-b3f7-3399a6708022', '가장 친한 어린 시절 친구 이름은?'),
                                                                   ('84e89b90-8db9-4168-8b2a-e83ca773b880', '3학년 때 살던 거리 이름은?'),
                                                                   ('62265fdb-97bf-4d60-99da-36410b3b2427', '첫 차의 제조사와 모델은?');

-- 여기서 memberRepositoryTest testData 실행 해야함

-- 회사정보
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('2e7b0a9e-0d2f-4f61-9985-422d5d08511b', 1101111129497, '카카오');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('370666c2-89b5-438e-8f26-55cf15e299ba', 1301110006246, '삼성전자');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('73c7a736-ecbe-40c3-9e05-38eaa62d319a', 1101112487050, '엘지전자');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('b652a2fb-e636-43da-a372-f43668afcf6b', 1101111707178, '네이버');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('e45a9a57-4123-419d-85ea-4406ddbccaf6', 1101115067718, '쿠팡');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('bc163660-c9fa-489e-a4db-ddf9cf9174c2', 1101114554063, '우아한형제들');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('0844a95b-f7a6-4b54-bfa2-aa77fa1dcf23', 1101117373098, '토스뱅크');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('09997d67-d268-4343-aa88-8dd8dbe6a3ce', 1101113320241, '넥슨코리아');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('249c08b9-fb4c-4f70-b37b-549f5214fb60', 1101113645772, '크래프톤');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('40bc9105-2416-4714-be63-9a4c7e89e1ad', 1101114731299, '넷마블');
INSERT INTO companies (com_id, reg_num, com_name) VALUES ('c5e0da6f-cc00-4687-a92f-b27927eec902', 1101111387748, '엔씨소프트');

-- Industry 데이터 삽입
INSERT INTO industry (industry_id, industry_name, industry_description) VALUES
                                                                            ('0d835a98-cb55-48e6-b737-a46d440c898f', 'Tech', 'Description of Tech'),
                                                                            ('724fdbed-3baa-4ee5-bf38-7fe91a8eac24', 'Health', 'Description of Health'),
                                                                            ('cf379f05-d1fc-48b1-87d6-957ac87a25a1', 'Finance', 'Description of Finance');

-- Board 데이터 삽입
INSERT INTO board (board_id, board_name, industry_id) VALUES
                                                          ('c3944381-98b4-4ffa-a292-0fb7c72fc32a', 'General', '0d835a98-cb55-48e6-b737-a46d440c898f'),
                                                          ('a2f4b0c9-e6d8-4dab-99a9-7380345b9d34', 'Tech Talk', '724fdbed-3baa-4ee5-bf38-7fe91a8eac24'),
                                                          ('f9f50c7e-8e18-4d17-9df7-e9dc45d237f1', 'Health Tips', 'cf379f05-d1fc-48b1-87d6-957ac87a25a1');

-- Document 데이터 삽입
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('0376bcf6-56bc-41ea-b51b-93d6fc4e284e', 'c3944381-98b4-4ffa-a292-0fb7c72fc32a', '0d835a98-cb55-48e6-b737-a46d440c898f', 'Doc Title 1', 'Doc Content 1', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('18e56635-9489-47b7-8303-27b207594fe0', 'e0a00056-9c52-4f21-aa07-b6c5b8648141', '724fdbed-3baa-4ee5-bf38-7fe91a8eac24', 'Doc Title 2', 'Doc Content 2', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('0a9a2804-69e7-427a-b354-c8e23c211526', '95c74ccd-5b0e-4c14-b250-928a79dddaa4', 'cf379f05-d1fc-48b1-87d6-957ac87a25a1', 'Doc Title 3', 'Doc Content 3', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('ed1b40e1-7ef6-42e6-96e3-dd3ff889e33d', 'c3944381-98b4-4ffa-a292-0fb7c72fc32a', '0d835a98-cb55-48e6-b737-a46d440c898f', 'Doc Title 4', 'Doc Content 4', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('8c6d5d14-d1b4-43ef-bf94-2a2282b5982e', 'e0a00056-9c52-4f21-aa07-b6c5b8648141', '724fdbed-3baa-4ee5-bf38-7fe91a8eac24', 'Doc Title 5', 'Doc Content 5', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('377d89a2-7d64-450d-a39c-2ab246b8fd90', '95c74ccd-5b0e-4c14-b250-928a79dddaa4', 'cf379f05-d1fc-48b1-87d6-957ac87a25a1', 'Doc Title 6', 'Doc Content 6', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('169e4f09-36de-47c3-995f-8eb510343bc8', 'c3944381-98b4-4ffa-a292-0fb7c72fc32a', '0d835a98-cb55-48e6-b737-a46d440c898f', 'Doc Title 7', 'Doc Content 7', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('e2fa141f-d946-48dd-b615-b399cedc9792', 'e0a00056-9c52-4f21-aa07-b6c5b8648141', '724fdbed-3baa-4ee5-bf38-7fe91a8eac24', 'Doc Title 8', 'Doc Content 8', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);
INSERT INTO Document (doc_id, board_id, industry_id, doc_title, doc_content, doc_create_date, doc_creator, doc_mod_date, doc_modifier, doc_visible) VALUES ('1888ac13-2ee1-4948-9b34-dc7586909f11', '95c74ccd-5b0e-4c14-b250-928a79dddaa4', 'cf379f05-d1fc-48b1-87d6-957ac87a25a1', 'Doc Title 9', 'Doc Content 9', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE);


-- comment 데이터 삽입
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('1fa6c1d2-b618-48e7-8af9-524baffecb9b', '0376bcf6-56bc-41ea-b51b-93d6fc4e284e', 'Comment Content 1 for Doc 1', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('8409ec4c-3bbd-42d6-a50c-43b131a41ef5', '0376bcf6-56bc-41ea-b51b-93d6fc4e284e', 'Comment Content 2 for Doc 1', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('87bfd6be-b04f-4102-b41f-bf7fb809c73f', '18e56635-9489-47b7-8303-27b207594fe0', 'Comment Content 1 for Doc 2', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('c4fe5a47-5541-4bf2-bc6a-5db7dbeda418', '18e56635-9489-47b7-8303-27b207594fe0', 'Comment Content 2 for Doc 2', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('eba41c35-4a38-45e5-b2df-8fe9f024c2c9', '0a9a2804-69e7-427a-b354-c8e23c211526', 'Comment Content 1 for Doc 3', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('93a6f64c-ed52-42cc-aba6-8780a3c8f980', '0a9a2804-69e7-427a-b354-c8e23c211526', 'Comment Content 2 for Doc 3', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('e34af262-5703-49e3-8296-02f4be224426', 'ed1b40e1-7ef6-42e6-96e3-dd3ff889e33d', 'Comment Content 1 for Doc 4', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('a86b32d7-107c-4952-9782-93cbd68041b5', 'ed1b40e1-7ef6-42e6-96e3-dd3ff889e33d', 'Comment Content 2 for Doc 4', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('95148fdc-c450-4009-bde0-6de896a16366', '8c6d5d14-d1b4-43ef-bf94-2a2282b5982e', 'Comment Content 1 for Doc 5', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('bbbb05c3-6aab-4212-9206-e1d370ea9393', '8c6d5d14-d1b4-43ef-bf94-2a2282b5982e', 'Comment Content 2 for Doc 5', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('75eb4833-fd51-4668-a1ff-1af210703228', '377d89a2-7d64-450d-a39c-2ab246b8fd90', 'Comment Content 1 for Doc 6', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('5f71c08d-21b7-4bb5-8a1b-9b1d5509c225', '377d89a2-7d64-450d-a39c-2ab246b8fd90', 'Comment Content 2 for Doc 6', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('13d63a63-19e7-4410-b37e-6f5f15c8d089', '169e4f09-36de-47c3-995f-8eb510343bc8', 'Comment Content 1 for Doc 7', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('901d17d9-d4f8-44d3-9fb8-51e4193f0e5e', '169e4f09-36de-47c3-995f-8eb510343bc8', 'Comment Content 2 for Doc 7', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('1ed8d0da-f4e7-40e9-9b17-a320c577cdb0', 'e2fa141f-d946-48dd-b615-b399cedc9792', 'Comment Content 1 for Doc 8', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('52caa26c-d255-411a-80b7-d2adae7b5b55', 'e2fa141f-d946-48dd-b615-b399cedc9792', 'Comment Content 2 for Doc 8', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('191106e9-3fa5-497b-bb91-fb20d137561b', '1888ac13-2ee1-4948-9b34-dc7586909f11', 'Comment Content 1 for Doc 9', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', NOW(), '4eed02a1-a986-4e6b-a48c-c67238797fab', TRUE, 'AnonyNickname1');
INSERT INTO comment (comment_id, doc_id, comment_content, comment_create_date, comment_creator, comment_mod_date, comment_modifier, comment_visible, anony_nickname) VALUES ('fd366566-5931-46aa-b47a-12d265c07c91', '1888ac13-2ee1-4948-9b34-dc7586909f11', 'Comment Content 2 for Doc 9', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', NOW(), '829629cf-5414-44de-9c8f-38848d9a2481', TRUE, 'AnonyNickname2');


-- like_it 데이터 삽입
INSERT INTO like_it (like_id, like_count) VALUES ('0376bcf6-56bc-41ea-b51b-93d6fc4e284e', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('18e56635-9489-47b7-8303-27b207594fe0', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('0a9a2804-69e7-427a-b354-c8e23c211526', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('ed1b40e1-7ef6-42e6-96e3-dd3ff889e33d', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('8c6d5d14-d1b4-43ef-bf94-2a2282b5982e', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('377d89a2-7d64-450d-a39c-2ab246b8fd90', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('169e4f09-36de-47c3-995f-8eb510343bc8', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('e2fa141f-d946-48dd-b615-b399cedc9792', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('1888ac13-2ee1-4948-9b34-dc7586909f11', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('f54b236d-88ac-4370-8bc0-f03690857e51', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('68a3671f-a243-4c39-9c9c-f55e562e753e', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('35057691-e6d0-401f-b85e-46ca30ed091d', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('d1dc89bb-b4e1-4194-8427-8597a992809a', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('1a667bda-def4-4e4b-883c-a3b77aa08028', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('a71748fa-0c9c-4e1e-b466-150153199342', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('db7c05b2-e443-4e94-b9c2-021024ddd67b', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('e57007ba-59d7-47b0-8675-023acf959ebf', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('c907b6a7-480f-48fd-8f49-d7463e18db81', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('ca7d48c8-e656-43bf-8ce2-dbe2d787ddcc', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('7c3a03e9-cd37-480d-a25c-3366da9ea9e8', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('22b04601-0dba-4f2e-bb01-9c039e4bb450', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('6d5a2970-eaa4-4373-87ce-417ffdd2b74f', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('0729e664-0957-4e85-a1c1-8df7de6747aa', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('114335aa-5f25-475f-901e-c1f90aa6e5fb', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('e32c2e6d-0e34-49f9-8a75-e3d7a01a297b', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('57afc6d6-e946-40a1-b278-d16a0af11fb7', 0);
INSERT INTO like_it (like_id, like_count) VALUES ('b77dda46-5fab-4fe9-841f-53496b548af5', 0);


-- viewership 데이터 삽입
INSERT INTO viewership (view_id, view_count) VALUES ('0376bcf6-56bc-41ea-b51b-93d6fc4e284e', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('18e56635-9489-47b7-8303-27b207594fe0', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('0a9a2804-69e7-427a-b354-c8e23c211526', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('ed1b40e1-7ef6-42e6-96e3-dd3ff889e33d', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('8c6d5d14-d1b4-43ef-bf94-2a2282b5982e', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('377d89a2-7d64-450d-a39c-2ab246b8fd90', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('169e4f09-36de-47c3-995f-8eb510343bc8', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('e2fa141f-d946-48dd-b615-b399cedc9792', 0);
INSERT INTO viewership (view_id, view_count) VALUES ('1888ac13-2ee1-4948-9b34-dc7586909f11', 0);


-- report 데이터 삽입
INSERT INTO report (report_id, report_content, report_date, report_judge, report_type, report_thing, report_content) VALUES ('78b0e53d-c683-46bb-88a5-2bdeac6c0fde', 'Report content for doc 1', NOW(), FALSE, 1, '0376bcf6-56bc-41ea-b51b-93d6fc4e284e', 'Inappropriate content');
INSERT INTO report (report_id, report_content, report_date, report_judge, report_type, report_thing, report_content) VALUES ('fbf1da5e-715f-4292-a8d3-29e8f1d33d5c', 'Report content for doc 2', NOW(), FALSE, 1, '18e56635-9489-47b7-8303-27b207594fe0', 'Inappropriate content');
INSERT INTO report (report_id, report_content, report_date, report_judge, report_type, report_thing, report_content) VALUES ('ad4660e3-3b1d-48e0-855f-094b321d6c9e', 'Report content for doc 3', NOW(), FALSE, 1, '0a9a2804-69e7-427a-b354-c8e23c211526', 'Inappropriate content');