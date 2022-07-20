package com.shop.entity;

import com.shop.dto.ReplyFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Getter
@Setter
@ToString
public class Reply extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long star;

    @Column(nullable = false)
    private String replyContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateReply(ReplyFormDto replyFormDto) {
        this.star = replyFormDto.getStar();
        this.replyContent = replyFormDto.getReplyContent();
    }
}
