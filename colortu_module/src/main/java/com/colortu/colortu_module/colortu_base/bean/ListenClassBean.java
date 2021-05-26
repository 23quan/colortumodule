package com.colortu.colortu_module.colortu_base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Code23
 * @time : 2021/4/2
 * @module : ListenClassBean
 * @describe :听写课bean
 */
public class ListenClassBean implements Serializable {
    private int code;
    private boolean success;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean implements Serializable {
        private int num;
        private int type;
        private List<PoetryVOSBean> poetryVOS;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<PoetryVOSBean> getPoetryVOS() {
            return poetryVOS;
        }

        public void setPoetryVOS(List<PoetryVOSBean> poetryVOS) {
            this.poetryVOS = poetryVOS;
        }

        public static class PoetryVOSBean implements Serializable {
            private int id;
            private String name;
            private String dynasty;
            private String auth;
            private String content;
            private String translation;
            private String audio;
            private String kaudio;
            private int gradeId;
            private String gradeName;
            private String gradeTitle;
            private List<WordsBean> words;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDynasty() {
                return dynasty;
            }

            public void setDynasty(String dynasty) {
                this.dynasty = dynasty;
            }

            public String getAuth() {
                return auth;
            }

            public void setAuth(String auth) {
                this.auth = auth;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getTranslation() {
                return translation;
            }

            public void setTranslation(String translation) {
                this.translation = translation;
            }

            public String getAudio() {
                return audio;
            }

            public void setAudio(String audio) {
                this.audio = audio;
            }

            public String getKaudio() {
                return kaudio;
            }

            public void setKaudio(String kaudio) {
                this.kaudio = kaudio;
            }

            public int getGradeId() {
                return gradeId;
            }

            public void setGradeId(int gradeId) {
                this.gradeId = gradeId;
            }

            public String getGradeName() {
                return gradeName;
            }

            public void setGradeName(String gradeName) {
                this.gradeName = gradeName;
            }

            public String getGradeTitle() {
                return gradeTitle;
            }

            public void setGradeTitle(String gradeTitle) {
                this.gradeTitle = gradeTitle;
            }

            public List<WordsBean> getWords() {
                return words;
            }

            public void setWords(List<WordsBean> words) {
                this.words = words;
            }

            public static class WordsBean implements Serializable {
                private int id;
                private int lessonId;
                private String word;
                private String wordDesc;
                private String wordAudioUrl;
                private String wordDescAudioUrl;
                private boolean playing;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getLessonId() {
                    return lessonId;
                }

                public void setLessonId(int lessonId) {
                    this.lessonId = lessonId;
                }

                public String getWord() {
                    return word;
                }

                public void setWord(String word) {
                    this.word = word;
                }

                public String getWordDesc() {
                    return wordDesc;
                }

                public void setWordDesc(String wordDesc) {
                    this.wordDesc = wordDesc;
                }

                public String getWordAudioUrl() {
                    return wordAudioUrl;
                }

                public void setWordAudioUrl(String wordAudioUrl) {
                    this.wordAudioUrl = wordAudioUrl;
                }

                public String getWordDescAudioUrl() {
                    return wordDescAudioUrl;
                }

                public void setWordDescAudioUrl(String wordDescAudioUrl) {
                    this.wordDescAudioUrl = wordDescAudioUrl;
                }

                public boolean isPlaying() {
                    return playing;
                }

                public void setPlaying(boolean playing) {
                    this.playing = playing;
                }
            }
        }
    }
}
