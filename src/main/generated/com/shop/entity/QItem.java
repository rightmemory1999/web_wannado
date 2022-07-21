package com.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1161068493L;

    public static final QItem item = new QItem("item");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<com.shop.constant.CoffeeBean> coffeeBean = createEnum("coffeeBean", com.shop.constant.CoffeeBean.class);

    public final StringPath coffeeTaste = createString("coffeeTaste");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath extraction = createString("extraction");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemDetail = createString("itemDetail");

    public final StringPath itemNm = createString("itemNm");

    public final EnumPath<com.shop.constant.ItemSellStatus> itemSellStatus = createEnum("itemSellStatus", com.shop.constant.ItemSellStatus.class);

    public final StringPath kind = createString("kind");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath origin = createString("origin");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final StringPath process = createString("process");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath roasteryDetail = createString("roasteryDetail");

    public final StringPath roasteryNm = createString("roasteryNm");

    public final NumberPath<Integer> stockNumber = createNumber("stockNumber", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

